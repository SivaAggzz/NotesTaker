package com.techneapps.notestaking.data.dao.notes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NoteObj.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {
    public abstract NotesDao getNotesDao();
}
