package com.techneapps.notestaking.ui.allnotesviewer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.databinding.ActivityAllNotesViewerBinding;
import com.techneapps.notestaking.helper.SortingHelper;
import com.techneapps.notestaking.ui.adapter.NotesAdapter;
import com.techneapps.notestaking.ui.addnote.AddNewNoteActivity;

import java.util.Collections;

public class AllNotesViewerActivity extends AppCompatActivity {

    private AllNotesViewerModel allNotesViewerModel;
    private ActivityAllNotesViewerBinding activityNotesViewerBinding;

    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_notes_viewer);
        setSupportActionBar(activityNotesViewerBinding.toolbar);
        allNotesViewerModel = ViewModelProviders.of(this).get(AllNotesViewerModel.class);
        activityNotesViewerBinding.addNoteFab.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), AddNewNoteActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        allNotesViewerModel.getNoteObjects().observe(this, noteObjs -> {
            activityNotesViewerBinding.notesRecyclerView.setLayoutManager(new LinearLayoutManager(AllNotesViewerActivity.this));
            Collections.sort(noteObjs, new SortingHelper.sortByDate());
            notesAdapter = new NotesAdapter(AllNotesViewerActivity.this, noteObjs);
            activityNotesViewerBinding.notesRecyclerView.setAdapter(notesAdapter);
        });
    }
}
