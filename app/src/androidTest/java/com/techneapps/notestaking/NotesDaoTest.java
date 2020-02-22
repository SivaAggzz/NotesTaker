package com.techneapps.notestaking;

import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.techneapps.notestaking.database.NotesDatabase;
import com.techneapps.notestaking.database.models.NoteObj;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4ClassRunner.class)
public class NotesDaoTest {
    private NotesDatabase notesDatabase;

    @Before
    public void initDb() {
        notesDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                NotesDatabase.class).build();
    }

    @After
    public void closeDb() {
        notesDatabase.close();
    }

    @Test
    public void insertNoteSavesData() {
        NoteObj cachedNote = NoteFactory.makeCachedNote();
        notesDatabase.getNotesDao().addNote(cachedNote);


        List<NoteObj> notes = notesDatabase.getNotesDao().getNotes();
        assert (notes.size() > 0);
    }

    @Test
    public void getNotesRetrievesData() {
        List<NoteObj> cachedNoteList = NoteFactory.makeCachedNoteList(5);
        for (NoteObj noteObj : cachedNoteList) {
            notesDatabase.getNotesDao().addNote(noteObj);
        }

        List<NoteObj> retrievedBufferoos = notesDatabase.getNotesDao().getNotes();
        assert (retrievedBufferoos == cachedNoteList);
    }

    @Test
    public void clearNoteClearsData() {
        NoteObj cachedNote = NoteFactory.makeCachedNote();
        notesDatabase.getNotesDao().addNote(cachedNote);

        notesDatabase.clearAllTables();
        assert (notesDatabase.getNotesDao().getNotes().isEmpty());
    }
}
