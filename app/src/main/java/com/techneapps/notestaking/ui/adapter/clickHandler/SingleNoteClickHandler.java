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
        //start new activity with the appropriate note to view it
        Intent viewSavedNoteIntent = new Intent(context, SingleNoteViewerActivity.class);
        viewSavedNoteIntent.putExtra("savedNote", noteObj);
        context.startActivity(viewSavedNoteIntent);
    }
}
