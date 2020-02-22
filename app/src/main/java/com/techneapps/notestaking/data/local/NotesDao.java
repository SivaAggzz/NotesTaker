package com.techneapps.notestaking.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.techneapps.notestaking.data.NoteObj;

import java.util.List;

@Dao
public interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNote(NoteObj noteObj);

    @Delete
    void deleteNote(NoteObj noteObj);

    @Update
    void updateNote(NoteObj noteObj);


    @Query("select * from notes")
    List<NoteObj> getNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteObj> noteObjs);


}
