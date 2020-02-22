package com.techneapps.notestaking.view.viewnote.allnotes;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.data.NoteObj;
import com.techneapps.notestaking.data.local.NotesRepo;
import com.techneapps.notestaking.databinding.ActivityAllNotesViewerBinding;
import com.techneapps.notestaking.util.AnimationHelper;
import com.techneapps.notestaking.util.MustMethods;
import com.techneapps.notestaking.util.SortingHelper;
import com.techneapps.notestaking.util.viewmodelfactory.NotesViewModelFactory;
import com.techneapps.notestaking.view.addnote.AddNewNoteActivity;
import com.techneapps.notestaking.view.settings.SettingsActivity;
import com.techneapps.notestaking.view.viewnote.allnotes.adapter.NotesAdapter;
import com.techneapps.notestaking.viewmodel.NotesViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import static com.techneapps.notestaking.util.MustMethods.showBeautifiedDialog;
import static com.techneapps.notestaking.util.MustMethods.showToast;

public class AllNotesViewerActivity extends AppCompatActivity implements OnSingleNoteClickListener {

    private NotesViewModel notesViewModel;
    private ActivityAllNotesViewerBinding activityNotesViewerBinding;

    private NotesAdapter notesAdapter;
    private boolean doubleBackToExitPressedOnce = false;
    private Handler handler;

    private boolean contextualDeleteFABShown = false;
    private DrawerArrowDrawable drawerArrowDrawable;
    private NoteObj tempNoteObj;

    private ArrayList<NoteObj> selectedNotes = new ArrayList<>();
    private NotesRepo notesRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_notes_viewer);
        setSupportActionBar(activityNotesViewerBinding.toolbar);
        drawerArrowDrawable = new DrawerArrowDrawable(this);
        drawerArrowDrawable.setColor(getResources().getColor(R.color.md_blue_600));
        activityNotesViewerBinding.toolbar.setNavigationIcon(drawerArrowDrawable);

        notesRepo = Room.databaseBuilder(this, NotesRepo.class, "notes.db").build();
        notesViewModel = ViewModelProviders.of(this, new NotesViewModelFactory(notesRepo))
                .get(NotesViewModel.class);


        handler = new Handler();
        activityNotesViewerBinding.addNoteFab.setOnClickListener(v -> {
            if (notesAdapter.getSelectedItemCount() > 0) {
                //show confirm multiple delete dialog
                showConfirmMultipleDeleteDialog();
            } else {
                //add new note function
                startActivity(new Intent(getApplicationContext(), AddNewNoteActivity.class));
            }
        });
        activityNotesViewerBinding.swipeRefreshRootLayout.setOnRefreshListener(this::onSwipeRefreshLayout);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //color NavigationBarColor to match UI
        getWindow().setNavigationBarColor(getResources().getColor(R.color.md_grey_900));
        loadSavedNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_notes_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clearAllNotes) {
            clearAllNotes();
        } else if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (notesAdapter.getSelectedItemCount() > 0) {
            resetToolbarIcon();
            resetFABToAdd();
            return;
        }

        //method to double tap to exit from home
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        showToast(this, getResources().getString(R.string.tap_again_to_exit));
        handler.postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    //click events for all notes view
    @Override
    public void onNoteClicked(NoteObj noteObj, int position) {
        //check if any note is previously selected
        if (notesAdapter.getSelectedItemCount() > 0) {
            onNoteLongClicked(position);
        } else {
            //no item selected -> so normal click method
            //start new activity with the appropriate note to view it
            MustMethods.startActivityWithExtra(this, noteObj);
        }

    }

    @Override
    public void onNoteLongClicked(int position) {
        //toggle this very position
        notesAdapter.toggleSelection(position);
        if (notesAdapter.isSelected(position)) {
            selectedNotes.add(notesAdapter.get(position));
        } else {
            selectedNotes.remove(notesAdapter.get(position));
        }
        //check for selected count and toggle contextual menus acc
        if (notesAdapter.getSelectedItemCount() > 0) {

            if (notesAdapter.getSelectedItemCount() == 1) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(notesAdapter.getSelectedItemCount() + " note selected");
            } else {
                Objects.requireNonNull(getSupportActionBar()).setTitle(notesAdapter.getSelectedItemCount() + " notes selected");
            }
            if (!contextualDeleteFABShown) {
                showContextualDeleteMenu();
                changeToolbarIconTOBack();
            }
        } else {
            resetFABToAdd();
            resetToolbarIcon();
        }
    }


    private void onSwipeRefreshLayout() {
        loadSavedNotes();
        activityNotesViewerBinding.swipeRefreshRootLayout.setRefreshing(false);
    }


    private void loadSavedNotes() {

        notesViewModel.getNoteObjects().observe(this, noteObjs -> {
            activityNotesViewerBinding.notesRecyclerView.setLayoutManager(new LinearLayoutManager(AllNotesViewerActivity.this));
            Collections.sort(noteObjs, new SortingHelper.sortByDate());
            setAdapter(noteObjs);
        });
    }

    private void setAdapter(ArrayList<NoteObj> noteObjs) {
        notesAdapter = new NotesAdapter(AllNotesViewerActivity.this, noteObjs);
        activityNotesViewerBinding.notesRecyclerView.setAdapter(notesAdapter);
        if (noteObjs.size() > 0) {
            //hide no notes UI
            activityNotesViewerBinding.noNotesUI.setVisibility(View.GONE);
            activityNotesViewerBinding.notesRecyclerView.setVisibility(View.VISIBLE);
        } else {
            //show no notes UI
            activityNotesViewerBinding.notesRecyclerView.setVisibility(View.GONE);
            activityNotesViewerBinding.noNotesUI.setVisibility(View.VISIBLE);
        }
    }

    private void showClearAllNotesConfirmation() {
        Dialog confirmClearNotesDialog = showBeautifiedDialog(this
                , getResources().getString(R.string.conf_clear_all_notes));
        ((TextView) confirmClearNotesDialog.findViewById(R.id.title)).setText(getResources().getString(R.string.confirm));
        confirmClearNotesDialog.findViewById(R.id.okBtn).setOnClickListener(v -> {
            //user pressed confirm button
            //method to clear all saved notes
            confirmClearNotesDialog.dismiss();
            notesViewModel.clearNotesObjects();
            setAdapter(new ArrayList<>());
            showToast(this, getResources().getString(R.string.cleared_all_notes));
        });
        confirmClearNotesDialog.show();

    }

    private void clearAllNotes() {
        showClearAllNotesConfirmation();

    }



    private void showConfirmMultipleDeleteDialog() {
        Dialog confirmMultipleDeleteDialog = showBeautifiedDialog(this
                , (notesAdapter.getSelectedItemCount() == 1)
                        ? getResources().getString(R.string.conf_delete_single_note) : getResources().getString(R.string.conf_delete_multiple_notes));
        ((TextView) confirmMultipleDeleteDialog.findViewById(R.id.title))
                .setText(getResources().getString(R.string.confirm));
        confirmMultipleDeleteDialog.findViewById(R.id.okBtn).setOnClickListener(v -> {
            //user pressed confirm button
            //method to
            deleteSelectedNotes();
            confirmMultipleDeleteDialog.dismiss();
        });
        confirmMultipleDeleteDialog.show();

    }
    private void deleteSelectedNotes() {
        //delete from db
        for (int i = 0; i < notesAdapter.getSelectedItemCount(); i++) {
            tempNoteObj = notesAdapter.get(notesAdapter.getSelectedItems().get(i));
            notesViewModel.deleteNote(tempNoteObj);
        }
        for (int i = 0; i < selectedNotes.size(); i++) {
            notesAdapter.removeItem(notesAdapter.getSelectedItems().get(i));
        }
        showToast(this, getString(R.string.notes_deleted));
        resetToolbarIcon();
        resetFABToAdd();
        //load fresh data from db
        // loadSavedNotes();

    }





    private void resetFABToAdd() {
        if (notesAdapter.getSelectedItemCount() > 0) {
            notesAdapter.clearSelection();
        }
        AnimationHelper.rotateFABToAdd(activityNotesViewerBinding.addNoteFab);
        contextualDeleteFABShown = false;

    }
    private void showContextualDeleteMenu() {
        contextualDeleteFABShown = true;
        AnimationHelper.rotateFABToDelete(activityNotesViewerBinding.addNoteFab);
    }

    private void resetToolbarIcon() {
        ObjectAnimator.ofFloat(drawerArrowDrawable, "progress", 0).start();
        selectedNotes.clear();
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.app_name));
    }
    private void changeToolbarIconTOBack() {
        ObjectAnimator.ofFloat(drawerArrowDrawable, "progress", 1).start();
    }

}
