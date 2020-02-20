package com.techneapps.notestaking.ui.addnote;

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

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.databinding.ActivityAddNewNoteBinding;
import com.techneapps.notestaking.helper.DeviceHelper;
import com.techneapps.notestaking.ui.singlenoteviewer.SingleNoteViewerActivity;

import java.util.Objects;

public class AddNewNoteActivity extends AppCompatActivity {

    private ActivityAddNewNoteBinding activityAddNewNoteBinding;
    private AddNewNoteModel addNewNoteModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddNewNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_note);
        addNewNoteModel = ViewModelProviders.of(this).get(AddNewNoteModel.class);
        initUI();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add new note");
        activityAddNewNoteBinding.titleEditTextLayout.setHintAnimationEnabled(false);
        activityAddNewNoteBinding.contentEditTextLayout.setHintAnimationEnabled(false);

        activityAddNewNoteBinding.contentEditTextLayout.requestFocus();
    }

    public void saveNote(View view) {
        if (validateFields()) {
            NoteObj currentNoteObj = getCurrentNoteObj();
            addNewNoteModel.saveNote(currentNoteObj, () -> {
                Intent savedNoteIntent = new Intent(this, SingleNoteViewerActivity.class);
                savedNoteIntent.putExtra("savedNote", currentNoteObj);
                finish();
                startActivity(savedNoteIntent);
            });

        }
    }

    private NoteObj getCurrentNoteObj() {
        NoteObj noteObj = new NoteObj();
        noteObj.setTitle(activityAddNewNoteBinding.titleEditText.getText().toString().trim());
        noteObj.setContent(activityAddNewNoteBinding.contentEditText.getText().toString().trim());
        noteObj.setTimeStamp(System.currentTimeMillis());
        return noteObj;
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(activityAddNewNoteBinding.titleEditText.getText().toString().trim())) {
            activityAddNewNoteBinding.titleEditTextLayout.setError("Enter Title text!");
            DeviceHelper.vibrateDevice(activityAddNewNoteBinding.titleEditTextLayout);
            return false;
        } else {
            activityAddNewNoteBinding.titleEditTextLayout.setError(null);
        }

        if (TextUtils.isEmpty(activityAddNewNoteBinding.contentEditText.getText().toString().trim())) {
            activityAddNewNoteBinding.contentEditTextLayout.setError("Enter Note text!");
            DeviceHelper.vibrateDevice(activityAddNewNoteBinding.contentEditTextLayout);
            return false;
        } else {
            activityAddNewNoteBinding.contentEditTextLayout.setError(null);
        }

        /*if (activityAddNewNoteBinding.titleEditText.getText().toString().trim().length()>100){
            activityAddNewNoteBinding.titleEditText.setError("Title too long.");
        }else {
            activityAddNewNoteBinding.titleEditText.setError(null);
        }*/
        return true;
    }


}
