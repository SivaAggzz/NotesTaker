package com.techneapps.notestaking.ui.editnote;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.databinding.ActivityNoteEditorBinding;

import java.util.Objects;

public class EditNoteActivity extends AppCompatActivity {

    private ActivityNoteEditorBinding activityNoteEditorBinding;
    private EditNoteModel editNoteModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNoteEditorBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_note_editor);
        editNoteModel = ViewModelProviders.of(this).get(EditNoteModel.class);
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
                editNoteModel.updateNote(getUpdatedNoteObj());
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
