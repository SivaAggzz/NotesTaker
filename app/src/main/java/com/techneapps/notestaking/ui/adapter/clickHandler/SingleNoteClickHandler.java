package com.techneapps.notestaking.ui.adapter.clickHandler;

import android.view.View;

import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.providers.interfaces.OnSingleNoteClickListener;

public class SingleNoteClickHandler {
    private OnSingleNoteClickListener onSingleNoteClickListener;
    private int position;


    public SingleNoteClickHandler(OnSingleNoteClickListener onSingleNoteClickListener, int position) {
        this.onSingleNoteClickListener = onSingleNoteClickListener;
        this.position = position;
    }

    public void onNoteClicked(View view, NoteObj noteObj) {
        onSingleNoteClickListener.onNoteClicked(noteObj, position);
    }

    public boolean onNoteLongClicked(View view) {
        onSingleNoteClickListener.onNoteLongClicked(position);
        return true;
    }

}
