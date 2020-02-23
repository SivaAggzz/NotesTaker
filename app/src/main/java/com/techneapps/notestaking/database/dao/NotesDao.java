package com.techneapps.notestaking.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.techneapps.notestaking.database.models.NoteObj;

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

    @Query("select * from notes WHERE note_id == :id")
    NoteObj getNoteById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteObj> noteObjs);


}
