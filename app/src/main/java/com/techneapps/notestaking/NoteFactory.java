package com.techneapps.notestaking;

import com.techneapps.notestaking.database.models.NoteObj;

import java.util.ArrayList;
import java.util.List;

class NoteFactory {
    //class for instrumented test
    static NoteObj makeCachedNote() {
        NoteObj noteObj = new NoteObj();
        noteObj.setTitle("Test title");
        noteObj.setContent("Test content");
        noteObj.setId(1);
        noteObj.setTimeStamp(System.currentTimeMillis());
        return noteObj;
    }

    static NoteObj makeCachedNoteForUpdate() {
        NoteObj noteObj = new NoteObj();
        noteObj.setTitle("New title");
        noteObj.setContent("New content");
        noteObj.setId(100);
        noteObj.setTimeStamp(System.currentTimeMillis());
        return noteObj;
    }

    static List<NoteObj> makeCachedNoteList() {
        List<NoteObj> cachedNots = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            NoteObj noteObj = new NoteObj();
            noteObj.setTitle("Test title " + i);
            noteObj.setContent("Test content " + i);
            noteObj.setId(i);
            //  noteObj.setTimeStamp(System.currentTimeMillis());
            cachedNots.add(noteObj);
        }
        return cachedNots;
    }
}
