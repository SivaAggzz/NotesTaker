package com.techneapps.notestaking;

import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.techneapps.notestaking.database.NotesDatabase;
import com.techneapps.notestaking.database.models.NoteObj;

import org.junit.After;
import org.junit.Assert;
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
        Assert.assertSame(notes.size(), 1);
    }

    @Test
    public void updateNoteUpdatesData() {
        NoteObj cachedNote = NoteFactory.makeCachedNoteForUpdate();
        notesDatabase.getNotesDao().addNote(cachedNote);

        NoteObj retrieved = notesDatabase.getNotesDao().getNoteById(100);
        retrieved.setTitle("Updated title");
        retrieved.setContent("Updated content");
        notesDatabase.getNotesDao().updateNote(retrieved);

        NoteObj retrievedUpdatedNote = notesDatabase.getNotesDao().getNoteById(100);
        Assert.assertEquals(retrievedUpdatedNote.getTitle(), "Updated title");
        Assert.assertEquals(retrievedUpdatedNote.getContent(), "Updated content");
    }

    @Test
    public void getNotesRetrievesData() {
        List<NoteObj> cachedNoteList = NoteFactory.makeCachedNoteList();
        for (NoteObj noteObj : cachedNoteList) {
            notesDatabase.getNotesDao().addNote(noteObj);
        }

        List<NoteObj> retrievedNotes = notesDatabase.getNotesDao().getNotes();
        Assert.assertSame(retrievedNotes.size(), 5);
    }

    @Test
    public void clearNoteClearsData() {
        NoteObj cachedNote = NoteFactory.makeCachedNote();
        notesDatabase.getNotesDao().addNote(cachedNote);

        notesDatabase.clearAllTables();
        Assert.assertSame(notesDatabase.getNotesDao().getNotes().isEmpty(), true);
    }
}
