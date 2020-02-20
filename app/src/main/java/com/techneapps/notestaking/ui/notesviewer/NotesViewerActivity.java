package com.techneapps.notestaking.ui.notesviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.databinding.ActivityNotesViewerBinding;
import com.techneapps.notestaking.ui.adapter.NotesAdapter;
import com.techneapps.notestaking.ui.addnote.AddNewNote;

public class NotesViewerActivity extends AppCompatActivity {

    private NotesViewerModel notesViewerModel;
    private ActivityNotesViewerBinding activityNotesViewerBinding;

    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotesViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_notes_viewer);
        setSupportActionBar(activityNotesViewerBinding.toolbar);
        notesViewerModel = ViewModelProviders.of(this).get(NotesViewerModel.class);

        notesViewerModel.getNoteObjects().observe(this, noteObjs -> {
            activityNotesViewerBinding.notesRecyclerView.setLayoutManager(new LinearLayoutManager(NotesViewerActivity.this));
            notesAdapter = new NotesAdapter(NotesViewerActivity.this, noteObjs);
            activityNotesViewerBinding.notesRecyclerView.setAdapter(notesAdapter);
        });

        activityNotesViewerBinding.addNoteFab.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), AddNewNote.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
