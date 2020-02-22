package com.techneapps.notestaking.view.viewnote.allnotes;

import com.techneapps.notestaking.data.NoteObj;

public interface OnSingleNoteClickListener {
    void onNoteClicked(NoteObj noteObj, int position);

    void onNoteLongClicked(int position);

}
