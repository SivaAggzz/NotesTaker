package com.techneapps.notestaking.view.viewnote.allnotes.adapter.clickHandler;

import android.view.View;

import com.techneapps.notestaking.data.NoteObj;
import com.techneapps.notestaking.view.viewnote.allnotes.OnSingleNoteClickListener;

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
