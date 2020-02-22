package com.techneapps.notestaking.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.techneapps.notestaking.database.dao.NotesDao;
import com.techneapps.notestaking.database.models.NoteObj;

@Database(entities = {NoteObj.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {
    public abstract NotesDao getNotesDao();
}
