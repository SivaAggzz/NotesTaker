package com.techneapps.notestaking.helper;

import com.techneapps.notestaking.data.dao.notes.NoteObj;

import java.util.Comparator;

public class SortingHelper {
    public static class sortByDate implements Comparator<NoteObj> {
        @Override
        public int compare(NoteObj noteObj1, NoteObj noteObj2) {
            //comparing note objects by their timestamp to show latest notes first
            return Long.compare(noteObj2.getTimeStamp(), noteObj1.getTimeStamp());
        }
    }
}
