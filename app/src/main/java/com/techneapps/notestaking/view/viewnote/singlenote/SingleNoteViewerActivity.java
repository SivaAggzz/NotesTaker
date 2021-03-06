package com.techneapps.notestaking.view.viewnote.singlenote;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.database.models.NoteObj;
import com.techneapps.notestaking.databinding.ActivitySingleNotesViewerBinding;

import java.util.Objects;

public class SingleNoteViewerActivity extends AppCompatActivity {

    private ActivitySingleNotesViewerBinding activitySingleNotesViewerBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySingleNotesViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_single_notes_viewer);
        initializeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //color NavigationBarColor to match UI
        getWindow().setNavigationBarColor(getResources().getColor(R.color.md_grey_900));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if the back button in toolbar is Selected
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView() {
        activitySingleNotesViewerBinding.setNote(getIncomingNote());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.saved_note));
    }

    private NoteObj getIncomingNote() {
        return (NoteObj) getIntent().getSerializableExtra("savedNote");
    }
}
