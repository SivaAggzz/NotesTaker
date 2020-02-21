package com.techneapps.notestaking.providers.interfaces;

import com.techneapps.notestaking.data.dao.notes.NoteObj;

public interface OnSingleNoteClickListener {
    void onNoteClicked(NoteObj noteObj, int position);

    boolean onNoteLongClicked(int position);

}
