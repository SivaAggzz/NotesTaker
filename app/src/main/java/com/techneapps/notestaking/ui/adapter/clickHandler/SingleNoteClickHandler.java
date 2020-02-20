package com.techneapps.notestaking.ui.adapter.clickHandler;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.ui.singlenoteviewer.SingleNoteViewerActivity;

public class SingleNoteClickHandler {
    private Context context;

    public SingleNoteClickHandler(Context context) {
        this.context = context;
    }

    public void viewNote(View view, NoteObj noteObj) {
        Intent savedNoteIntent = new Intent(context, SingleNoteViewerActivity.class);
        savedNoteIntent.putExtra("savedNote", noteObj);
        context.startActivity(savedNoteIntent);
    }
}
