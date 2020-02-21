package com.techneapps.notestaking.ui.allnotesviewer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.databinding.ActivityAllNotesViewerBinding;
import com.techneapps.notestaking.helper.AnimationHelper;
import com.techneapps.notestaking.helper.SortingHelper;
import com.techneapps.notestaking.providers.interfaces.OnSingleNoteClickListener;
import com.techneapps.notestaking.providers.interfaces.OnSwipeListener;
import com.techneapps.notestaking.ui.adapter.NotesAdapter;
import com.techneapps.notestaking.ui.adapter.swipelistener.SwipeListener;
import com.techneapps.notestaking.ui.addnote.AddNewNoteActivity;
import com.techneapps.notestaking.ui.singlenoteviewer.SingleNoteViewerActivity;

import java.util.ArrayList;
import java.util.Collections;

import static com.techneapps.notestaking.helper.MustMethods.showBeautifiedDialog;
import static com.techneapps.notestaking.helper.MustMethods.showToast;

public class AllNotesViewerActivity extends AppCompatActivity implements OnSingleNoteClickListener, OnSwipeListener {

    private AllNotesViewerModel allNotesViewerModel;
    private ActivityAllNotesViewerBinding activityNotesViewerBinding;

    private NotesAdapter notesAdapter;
    private boolean doubleBackToExitPressedOnce = false;
    private Handler handler;

    private boolean contextualDeleteFABShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_notes_viewer);
        setSupportActionBar(activityNotesViewerBinding.toolbar);
        allNotesViewerModel = ViewModelProviders.of(this).get(AllNotesViewerModel.class);
        handler = new Handler();
        activityNotesViewerBinding.addNoteFab.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), AddNewNoteActivity.class)));
        activityNotesViewerBinding.swipeRefreshRootLayout.setOnRefreshListener(this::onSwipeRefreshLayout);
    }

    private void onSwipeRefreshLayout() {
        loadSavedNotes();
        activityNotesViewerBinding.swipeRefreshRootLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //color NavigationBarColor to match UI
        getWindow().setNavigationBarColor(getResources().getColor(R.color.md_grey_900));
        loadSavedNotes();
    }

    private void loadSavedNotes() {
        allNotesViewerModel.getNoteObjects().observe(this, noteObjs -> {
            SwipeListener swipeListener = new SwipeListener(AllNotesViewerActivity.this);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeListener);
            itemTouchHelper.attachToRecyclerView(activityNotesViewerBinding.notesRecyclerView);
            activityNotesViewerBinding.notesRecyclerView.setLayoutManager(new LinearLayoutManager(AllNotesViewerActivity.this));
            Collections.sort(noteObjs, new SortingHelper.sortByDate());
            setAdapter(noteObjs);
        });
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
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (notesAdapter.getSelectedItemCount() > 0) {
            resetFABToAdd();
            return;
        }

        //method to double tap to exit from home
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.tap_again_to_exit), Toast.LENGTH_SHORT).show();
        handler.postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    private void clearAllNotes() {
        showClearAllNotesConfirmation();

    }

    private void showClearAllNotesConfirmation() {
        Dialog confirmClearNotesDialog = showBeautifiedDialog(this
                , getResources().getString(R.string.conf_clear_all_notes));
        ((TextView) confirmClearNotesDialog.findViewById(R.id.title)).setText(getResources().getString(R.string.confirm));
        confirmClearNotesDialog.findViewById(R.id.okBtn).setOnClickListener(v -> {
            //user pressed confirm button
            //method to clear all saved notes
            confirmClearNotesDialog.dismiss();
            allNotesViewerModel.clearNotesObjects();
            setAdapter(new ArrayList<>());
            showToast(this, getResources().getString(R.string.cleared_all_notes));
        });
        confirmClearNotesDialog.show();

    }


    private void setAdapter(ArrayList<NoteObj> noteObjs) {
        notesAdapter = new NotesAdapter(AllNotesViewerActivity.this, noteObjs);
        activityNotesViewerBinding.notesRecyclerView.setAdapter(notesAdapter);
        if (noteObjs.size() > 0) {
            //hide no notes UI
        } else {
            //show no notes UI
        }
    }

    @Override
    public void onNoteClicked(NoteObj noteObj, int position) {
        //check if any note is previously selected
        if (notesAdapter.getSelectedItemCount() > 0) {
            onNoteLongClicked(position);
        } else {
            //no item selected -> so normal click method
            //start new activity with the appropriate note to view it
            Intent viewSavedNoteIntent = new Intent(this, SingleNoteViewerActivity.class);
            viewSavedNoteIntent.putExtra("savedNote", noteObj);
            startActivity(viewSavedNoteIntent);
        }

    }

    @Override
    public boolean onNoteLongClicked(int position) {
        //toggle this very position
        notesAdapter.toggleSelection(position);
        //check for selected count and toggle contextual menus acc
        if (notesAdapter.getSelectedItemCount() > 0) {
            if (!contextualDeleteFABShown) {
                showContextualDeleteMenu();
            }
        } else {
            resetFABToAdd();
        }
        return true;
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

    @Override
    public void onSwipedLeft() {
        showToast(this, "onSwipedLeft");
    }

    @Override
    public void onSwipedRight() {
        showToast(this, "onSwipedRight");

    }
}
