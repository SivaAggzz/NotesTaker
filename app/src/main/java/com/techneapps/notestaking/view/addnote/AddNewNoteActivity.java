package com.techneapps.notestaking.view.addnote;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.data.NoteObj;
import com.techneapps.notestaking.data.local.NotesRepo;
import com.techneapps.notestaking.databinding.ActivityAddNewNoteBinding;
import com.techneapps.notestaking.util.MustMethods;
import com.techneapps.notestaking.util.preference.preferencecontroller.UserPreferenceGetterHelper;
import com.techneapps.notestaking.util.viewmodelfactory.NotesViewModelFactory;
import com.techneapps.notestaking.view.viewnote.singlenote.SingleNoteViewerActivity;
import com.techneapps.notestaking.viewmodel.NotesViewModel;

import java.util.Objects;

public class AddNewNoteActivity extends AppCompatActivity {

    private ActivityAddNewNoteBinding activityAddNewNoteBinding;
    private NotesViewModel notesViewModel;
    private NotesRepo notesRepo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddNewNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_note);
        notesRepo = Room.databaseBuilder(this, NotesRepo.class, "notes.db").build();
        notesViewModel = ViewModelProviders.of(this, new NotesViewModelFactory(notesRepo))
                .get(NotesViewModel.class);

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //color NavigationBarColor to match UI
        getWindow().setNavigationBarColor(getResources().getColor(R.color.md_grey_900));

    }
    @Override
    public void onBackPressed() {
        if (UserPreferenceGetterHelper.isSaveOnExit(this)) {
            if (validateFieldsWithoutError()) {
                saveValidatedNoteWithoutPreview();
            }
        }
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if the back button in toolbar is Selected
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_new_note));
        activityAddNewNoteBinding.titleEditTextLayout.setHintAnimationEnabled(false);
        activityAddNewNoteBinding.contentEditTextLayout.setHintAnimationEnabled(false);

        //requestFocus to show cursor blinking in this particular view
        activityAddNewNoteBinding.contentEditTextLayout.requestFocus();
    }

    public void saveNoteWithPreview(@Nullable View view) {
        if (validateFields()) {
            NoteObj currentNoteObj = getCurrentNoteObj();
            notesViewModel.addNote(currentNoteObj, () -> {

                Intent savedNoteIntent = new Intent(this, SingleNoteViewerActivity.class);
                savedNoteIntent.putExtra("savedNote", currentNoteObj);
                finish();
                startActivity(savedNoteIntent);

            });

        }
    }

    public void saveValidatedNoteWithoutPreview() {
        NoteObj currentNoteObj = getCurrentNoteObj();
        notesViewModel.addNote(currentNoteObj, () -> {

        });

    }

    private NoteObj getCurrentNoteObj() {
        NoteObj noteObj = new NoteObj();
        noteObj.setTitle(activityAddNewNoteBinding.titleEditText.getText().toString().trim());
        noteObj.setContent(activityAddNewNoteBinding.contentEditText.getText().toString().trim());
        noteObj.setTimeStamp(System.currentTimeMillis());
        return noteObj;
    }

    private boolean validateFields() {
        //returns boolean value according to the result found while validating

        //validate title edit text
        if (TextUtils.isEmpty(activityAddNewNoteBinding.titleEditText.getText().toString().trim())) {
            activityAddNewNoteBinding.titleEditTextLayout.setError("Enter Title text!");
            MustMethods.vibrateDevice(activityAddNewNoteBinding.titleEditTextLayout);
            return false;
        } else {
            activityAddNewNoteBinding.titleEditTextLayout.setError(null);
        }

        //validate content edit text
        if (TextUtils.isEmpty(activityAddNewNoteBinding.contentEditText.getText().toString().trim())) {
            activityAddNewNoteBinding.contentEditTextLayout.setError("Enter Note text!");
            MustMethods.vibrateDevice(activityAddNewNoteBinding.contentEditTextLayout);
            return false;
        } else {
            activityAddNewNoteBinding.contentEditTextLayout.setError(null);
        }

        //validation of title edit text for max 100 chars is done by XML
         /*if (activityAddNewNoteBinding.titleEditText.getText().toString().trim().length()>100){
            activityAddNewNoteBinding.titleEditText.setError("Title too long.");
        }else {
            activityAddNewNoteBinding.titleEditText.setError(null);
        }*/
        return true;
    }

    private boolean validateFieldsWithoutError() {
        //returns boolean value according to the result found while validating

        //validate title edit text
        if (TextUtils.isEmpty(activityAddNewNoteBinding.titleEditText.getText().toString().trim())) {
            return false;
        }

        //validate content edit text
        return !TextUtils.isEmpty(activityAddNewNoteBinding.contentEditText.getText().toString().trim());
    }
}
