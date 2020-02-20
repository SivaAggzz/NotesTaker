package com.techneapps.notestaking.ui.singlenoteviewer;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.databinding.ActivitySingleNotesViewerBinding;

import java.util.Objects;

public class SingleNoteViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySingleNotesViewerBinding activitySingleNotesViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_single_notes_viewer);
        activitySingleNotesViewerBinding.setNote(getIncomingNote());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Saved note");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if the back button in toolbar is Selected
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private NoteObj getIncomingNote() {
        return getIntent().getParcelableExtra("savedNote");
    }
}
