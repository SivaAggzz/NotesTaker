package com.techneapps.notestaking.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.techneapps.notestaking.data.NoteObj;

@Database(entities = {NoteObj.class}, version = 1)
public abstract class NotesRepo extends RoomDatabase {
    public abstract NotesDao getNotesDao();
}
