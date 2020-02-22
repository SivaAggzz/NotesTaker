package com.techneapps.notestaking.view.listener;

import com.techneapps.notestaking.database.models.NoteObj;

public interface OnSingleNoteClickListener {
    void onNoteClicked(NoteObj noteObj, int position);

    void onNoteLongClicked(int position);

}
