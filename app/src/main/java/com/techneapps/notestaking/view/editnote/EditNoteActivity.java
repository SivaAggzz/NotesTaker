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
import com.techneapps.notestaking.data.NoteObj;
import com.techneapps.notestaking.data.local.NotesRepo;
import com.techneapps.notestaking.databinding.ActivityNoteEditorBinding;
import com.techneapps.notestaking.util.viewmodelfactory.NotesViewModelFactory;
import com.techneapps.notestaking.viewmodel.NotesViewModel;

import java.util.Objects;

public class EditNoteActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding activityNoteEditorBinding;
    private NotesViewModel notesViewModel;
    private NotesRepo notesRepo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNoteEditorBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_note_editor);
        notesRepo = Room.databaseBuilder(this, NotesRepo.class, "notes.db").build();

        notesViewModel = ViewModelProviders.of(this, new NotesViewModelFactory(notesRepo))
                .get(NotesViewModel.class);
        activityNoteEditorBinding.setNote(getIncomingNote());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
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

    @Override
    protected void onResume() {
        super.onResume();
        //color NavigationBarColor to match UI
        getWindow().setNavigationBarColor(getResources().getColor(R.color.md_grey_900));

    }


    private NoteObj getIncomingNote() {
        return (NoteObj) getIntent().getSerializableExtra("savedNote");
    }
}
