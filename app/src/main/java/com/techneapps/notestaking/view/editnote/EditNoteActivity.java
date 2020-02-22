package com.techneapps.notestaking.view.editnote;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.database.NotesDatabase;
import com.techneapps.notestaking.database.models.NoteObj;
import com.techneapps.notestaking.databinding.ActivityNoteEditorBinding;
import com.techneapps.notestaking.util.viewmodelfactory.NotesViewModelFactory;
import com.techneapps.notestaking.viewModel.NotesViewModel;

import java.util.Objects;

public class EditNoteActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding activityNoteEditorBinding;
    private NotesViewModel notesViewModel;
    private NotesDatabase notesDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNoteEditorBinding = DataBindingUtil.setContentView(this, R.layout.activity_note_editor);
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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isNoteChanged()) {
            if (validateNoteFields()) {
                notesViewModel.updateNote(getUpdatedNoteObj());
            }
        }
        super.onBackPressed();
    }

    //initialization methods
    private void initializeView() {
        notesDatabase = Room.databaseBuilder(this, NotesDatabase.class, "notes.db").build();
        activityNoteEditorBinding.setNote(getIncomingNote());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        initializeViewModels();
    }

    private void initializeViewModels() {
        notesViewModel = ViewModelProviders.of(this, new NotesViewModelFactory(notesDatabase))
                .get(NotesViewModel.class);
    }


    private NoteObj getUpdatedNoteObj() {
        NoteObj noteObj = getIncomingNote();
        noteObj.setTitle(activityNoteEditorBinding.titleEditText.getText().toString().trim());
        noteObj.setContent(activityNoteEditorBinding.contentEditText.getText().toString().trim());
        noteObj.setTimeStamp(System.currentTimeMillis());
        return noteObj;
    }

    private boolean validateNoteFields() {
        //validate title edit text
        if (TextUtils.isEmpty(activityNoteEditorBinding.titleEditText.getText().toString().trim())) {
            return false;
        }

        //validate content edit text
        return !TextUtils.isEmpty(activityNoteEditorBinding.contentEditText.getText().toString().trim());
    }

    private boolean isNoteChanged() {
        return !getIncomingNote()
                .getContent()
                .trim()
                .equalsIgnoreCase(activityNoteEditorBinding.contentEditText.getText().toString().trim())
                ||
                !getIncomingNote()
                        .getTitle()
                        .trim()
                        .equalsIgnoreCase(activityNoteEditorBinding.titleEditText.getText().toString().trim());
    }

    private NoteObj getIncomingNote() {
        return (NoteObj) getIntent().getSerializableExtra("savedNote");
    }
}
