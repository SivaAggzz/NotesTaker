package com.techneapps.notestaking;

import com.techneapps.notestaking.database.models.NoteObj;

import java.util.ArrayList;
import java.util.List;

class NoteFactory {
    static NoteObj makeCachedNote() {
        NoteObj noteObj = new NoteObj();
        noteObj.setTitle("Test title");
        noteObj.setContent("Test content");
        noteObj.setTimeStamp(System.currentTimeMillis());
        return noteObj;
    }

    static List<NoteObj> makeCachedNoteList(int count) {
        List<NoteObj> cachedNots = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NoteObj noteObj = new NoteObj();
            noteObj.setTitle("Test title " + i);
            noteObj.setContent("Test content " + i);
            noteObj.setTimeStamp(System.currentTimeMillis());
            cachedNots.add(noteObj);
        }
        return cachedNots;
    }
}
