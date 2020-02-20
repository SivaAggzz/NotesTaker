package com.techneapps.notestaking.ui.allnotesviewer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.databinding.ActivityAllNotesViewerBinding;
import com.techneapps.notestaking.helper.SortingHelper;
import com.techneapps.notestaking.ui.adapter.NotesAdapter;
import com.techneapps.notestaking.ui.addnote.AddNewNoteActivity;

import java.util.ArrayList;
import java.util.Collections;

public class AllNotesViewerActivity extends AppCompatActivity {

    private AllNotesViewerModel allNotesViewerModel;
    private ActivityAllNotesViewerBinding activityNotesViewerBinding;

    private NotesAdapter notesAdapter;
    private boolean doubleBackToExitPressedOnce = false;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_notes_viewer);
        setSupportActionBar(activityNotesViewerBinding.toolbar);
        allNotesViewerModel = ViewModelProviders.of(this).get(AllNotesViewerModel.class);
        handler = new Handler();
        activityNotesViewerBinding.addNoteFab.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), AddNewNoteActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //color NavigationBarColor to match UI
        getWindow().setNavigationBarColor(getResources().getColor(R.color.md_grey_900));
        allNotesViewerModel.getNoteObjects().observe(this, noteObjs -> {
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
        //method to double tap to exit from home
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.tap_again_to_exit), Toast.LENGTH_SHORT).show();
        handler.postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    //method to clear all saved notes
    private void clearAllNotes() {
        allNotesViewerModel.clearNotesObjects();
        setAdapter(new ArrayList<>());
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

}
