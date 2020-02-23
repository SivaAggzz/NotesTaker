package com.techneapps.notestaking.view.viewnote.allnotes;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.database.NotesDatabase;
import com.techneapps.notestaking.database.models.NoteObj;
import com.techneapps.notestaking.databinding.ActivityAllNotesViewerBinding;
import com.techneapps.notestaking.util.AnimationHelper;
import com.techneapps.notestaking.util.MustMethods;
import com.techneapps.notestaking.util.SortingHelper;
import com.techneapps.notestaking.util.viewmodelfactory.NotesViewModelFactory;
import com.techneapps.notestaking.view.adapters.NotesAdapter;
import com.techneapps.notestaking.view.addnote.AddNewNoteActivity;
import com.techneapps.notestaking.view.editnote.EditNoteActivity;
import com.techneapps.notestaking.view.listener.OnNotesDeletedListener;
import com.techneapps.notestaking.view.listener.OnSingleNoteClickListener;
import com.techneapps.notestaking.view.settings.SettingsActivity;
import com.techneapps.notestaking.viewModel.NotesViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.techneapps.notestaking.util.MustMethods.showBeautifiedDialog;
import static com.techneapps.notestaking.util.MustMethods.showToast;

public class AllNotesViewerActivity extends AppCompatActivity implements OnSingleNoteClickListener, OnNotesDeletedListener {

    private ActivityAllNotesViewerBinding activityNotesViewerBinding;

    private NotesViewModel notesViewModel;
    private NotesDatabase notesDatabase;
    private NotesAdapter notesAdapter;

    private Handler handler;

    private DrawerArrowDrawable drawerArrowDrawable;

    private boolean doubleBackToExitPressedOnce = false;
    private boolean contextualDeleteFABShown = false;
    private Menu myMenu;
    private boolean showConfirmMultipleDeleteDialogShown = false;
    private boolean showClearAllNotesConfirmationShown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_notes_viewer);
        initializeView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("delete_dialog_shown", showConfirmMultipleDeleteDialogShown);
        outState.putBoolean("clear_all_dialog_shown", showClearAllNotesConfirmationShown);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //needed to real scenarios
       /* if (savedInstanceState.getBoolean("delete_dialog_shown")){
            showConfirmMultipleDeleteDialog();
        }else if (savedInstanceState.getBoolean("clear_all_dialog_shown")){
            showClearAllNotesConfirmation();
        }*/
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
        this.myMenu = menu;
        getMenuInflater().inflate(R.menu.all_notes_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clearAllNotes) {
            showClearAllNotesConfirmation();
        } else if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!checkForItemSelectionOnBackPressed()) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            } else {
                prepareDoubleBackToExitOnBackPressed();
            }
        }
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
            MustMethods.startActivityWithExtra(this, noteObj, EditNoteActivity.class);
        }

    }

    @Override
    public void onNoteLongClicked(int position) {
        //toggle this very position
        notesAdapter.toggleSelection(position);
        if (notesAdapter.isSelected(position)) {
            notesViewModel.addToSelectedList(notesAdapter.get(position));
        } else {
            notesViewModel.removeFromSelectedList(notesAdapter.get(position));
        }
        //check for selected count and toggle contextual menus acc
        if (notesAdapter.getSelectedItemCount() > 0) {
            if (notesAdapter.getSelectedItemCount() == 1) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(notesAdapter.getSelectedItemCount() + " note selected");
            } else {
                Objects.requireNonNull(getSupportActionBar()).setTitle(notesAdapter.getSelectedItemCount() + " notes selected");
            }
            if (!contextualDeleteFABShown) {
                animateFABToDelete();
                animateToolbarIconToSelected();
                hideMenuItems();
            }
        } else {
            animateFABToAdd();
            animateToolbarIconToDefault();
        }
    }

    @Override
    public void OnNotesDeleted() {
        //called after notes have been deleted from DB
        //remove items from recyclerview
        for (int i = notesViewModel.getSelectedNotes().size() - 1; i >= 0; i--) {
            notesAdapter.removeItem(notesAdapter.getSelectedItems().get(i));
        }
        //notesAdapter.removeSelectedItems();
        checkForNoNotesUI();
        showToast(this, getString(R.string.notes_deleted));
        animateToolbarIconToDefault();
        animateFABToAdd();
    }

    private void onSwipeRefreshLayout() {
        loadSavedNotes();
        activityNotesViewerBinding.swipeRefreshRootLayout.setRefreshing(false);
    }

    //initialization methods
    private void initializeView() {
        setSupportActionBar(activityNotesViewerBinding.toolbar);
        drawerArrowDrawable = new DrawerArrowDrawable(this);
        drawerArrowDrawable.setColor(getResources().getColor(R.color.md_white_1000));
        activityNotesViewerBinding.toolbar.setNavigationIcon(drawerArrowDrawable);

        activityNotesViewerBinding.swipeRefreshRootLayout.setOnRefreshListener(this::onSwipeRefreshLayout);

        notesDatabase = Room.databaseBuilder(this, NotesDatabase.class, "notes.db").build();
        handler = new Handler();
        initializeViewModels();
        initializeFAB();

    }

    private void initializeViewModels() {
        notesViewModel = ViewModelProviders.of(this, new NotesViewModelFactory(notesDatabase))
                .get(NotesViewModel.class);

    }

    private void initializeFAB() {
        activityNotesViewerBinding.addNoteFab.setOnClickListener(v -> onFABClicked());
    }

    //menu methods
    private void hideMenuItems() {
        myMenu.getItem(0).setVisible(false);
        myMenu.getItem(1).setVisible(false);
    }

    private void showMenuItems() {
        myMenu.getItem(0).setVisible(true);
        myMenu.getItem(1).setVisible(true);
    }


    //helper methods for onBackPressed
    private boolean checkForItemSelectionOnBackPressed() {
        //method to check if any item is currently selected
        if (notesAdapter.getSelectedItemCount() > 0) {
            animateToolbarIconToDefault();
            animateFABToAdd();
            return true;
        }
        return false;
    }

    private void prepareDoubleBackToExitOnBackPressed() {
        //method to double tap to exit from home if already back pressed once
        doubleBackToExitPressedOnce = true;
        showToast(this, getResources().getString(R.string.tap_again_to_exit));
        handler.postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    //core method responsible to retrieve and set data
    private void loadSavedNotes() {
        notesViewModel.getAllNotes().observe(this, noteObjs -> {
            activityNotesViewerBinding.notesRecyclerView.setLayoutManager(new LinearLayoutManager(AllNotesViewerActivity.this));
            Collections.sort(noteObjs, new SortingHelper.sortByDate());
            setAdapter(noteObjs);
        });
    }

    private void setAdapter(List<NoteObj> noteObjs) {
        notesAdapter = new NotesAdapter(AllNotesViewerActivity.this, noteObjs);
        activityNotesViewerBinding.notesRecyclerView.setAdapter(notesAdapter);
        checkForNoNotesUI();
    }

    private void checkForNoNotesUI() {
        if (notesAdapter.getItemCount() > 0) {
            //hide no notes UI
            activityNotesViewerBinding.noNotesUI.setVisibility(View.GONE);
            activityNotesViewerBinding.notesRecyclerView.setVisibility(View.VISIBLE);
        } else {
            //show no notes UI
            activityNotesViewerBinding.notesRecyclerView.setVisibility(View.GONE);
            activityNotesViewerBinding.noNotesUI.setVisibility(View.VISIBLE);
        }
    }


    //animation methods
    private void animateFABToAdd() {
        if (notesAdapter.getSelectedItemCount() > 0) {
            notesAdapter.clearSelection();
        }
        AnimationHelper.rotateFABToAdd(activityNotesViewerBinding.addNoteFab);
        showMenuItems();
        contextualDeleteFABShown = false;

    }

    private void animateFABToDelete() {
        contextualDeleteFABShown = true;
        AnimationHelper.rotateFABToDelete(activityNotesViewerBinding.addNoteFab);
    }

    private void animateToolbarIconToDefault() {
        ObjectAnimator.ofFloat(drawerArrowDrawable, "progress", 0).start();
        notesViewModel.clearSelectedList();
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.app_name));
    }

    private void animateToolbarIconToSelected() {
        ObjectAnimator.ofFloat(drawerArrowDrawable, "progress", 1).start();
    }


    //ui methods related to FAB
    private void onFABClicked() {
        if (notesViewModel.getSelectedNotes().size() > 0) {
            //show confirm multiple delete dialog
            showConfirmMultipleDeleteDialog();
        } else {
            //add new note function
            MustMethods.startActivity(this, AddNewNoteActivity.class);
        }
    }

    private void showConfirmMultipleDeleteDialog() {
        Dialog confirmMultipleDeleteDialog = showBeautifiedDialog(this
                , (notesViewModel.getSelectedNotes().size() == 1)
                        ? getResources().getString(R.string.conf_delete_single_note)
                        : getResources().getString(R.string.conf_delete_multiple_notes)
                , getResources().getString(R.string.confirm));
        confirmMultipleDeleteDialog.findViewById(R.id.okBtn).setOnClickListener(v -> {
            //user pressed confirm button
            notesViewModel.deleteSelectedNotes(this);
            confirmMultipleDeleteDialog.dismiss();
        });
        confirmMultipleDeleteDialog.setOnDismissListener(dialog -> showConfirmMultipleDeleteDialogShown = false);
        confirmMultipleDeleteDialog.show();
        showConfirmMultipleDeleteDialogShown = true;
    }

    private void showClearAllNotesConfirmation() {

        Dialog confirmClearNotesDialog = showBeautifiedDialog(this
                , getResources().getString(R.string.conf_clear_all_notes), getResources().getString(R.string.confirm));
        confirmClearNotesDialog.findViewById(R.id.okBtn).setOnClickListener(v -> {
            //user pressed confirm button
            //method to clear all saved notes
            confirmClearNotesDialog.dismiss();
            notesViewModel.clearNotesObjects();
            setAdapter(new ArrayList<>());
            showToast(this, getResources().getString(R.string.cleared_all_notes));
        });
        confirmClearNotesDialog.setOnDismissListener(dialog -> showClearAllNotesConfirmationShown = false);
        confirmClearNotesDialog.show();
        showClearAllNotesConfirmationShown = true;

    }


}
