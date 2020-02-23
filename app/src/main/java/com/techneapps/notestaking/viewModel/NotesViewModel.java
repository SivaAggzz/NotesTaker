package com.techneapps.notestaking.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techneapps.notestaking.database.NotesDatabase;
import com.techneapps.notestaking.database.models.NoteObj;
import com.techneapps.notestaking.repositories.NotesRepository;
import com.techneapps.notestaking.view.listener.OnNoteSavedListener;
import com.techneapps.notestaking.view.listener.OnNotesDeletedListener;

import java.util.ArrayList;
import java.util.List;

public class NotesViewModel extends ViewModel {
    //Data to provide to View
    private MutableLiveData<List<NoteObj>> noteObjects;
    //Our Repo
    private NotesRepository notesRepository;
    //Our selected notes
    private ArrayList<NoteObj> selectedNotes = new ArrayList<>();


    public NotesViewModel(NotesDatabase notesDatabase) {
        notesRepository = new NotesRepository(notesDatabase);
        noteObjects = notesRepository.getNoteObjects();
    }

    public MutableLiveData<List<NoteObj>> getAllNotes() {
        return notesRepository.getNoteObjects();
    }

    //method to delete all saved notes from Room database using RXJava
    public void clearNotesObjects() {
        notesRepository.clearNotesObjects();
    }

    //method to delete a saved note from Room database using RXJava
    private void deleteNote(NoteObj noteObj) {
        notesRepository.deleteNote(noteObj);
    }

    //method to save note to Room database using RXJava
    public void updateNote(NoteObj noteObj) {
        notesRepository.updateNote(noteObj);
    }

    //method to add note to Room database using RXJava
    public void addNote(NoteObj noteObj, OnNoteSavedListener onNoteSavedListener) {
        notesRepository.addNote(noteObj, onNoteSavedListener);
    }


    //ui methods
    public void deleteSelectedNotes(OnNotesDeletedListener onNotesDeletedListener) {
        //delete from db
        for (NoteObj noteObj : selectedNotes) {
            deleteNote(noteObj);
        }

        onNotesDeletedListener.OnNotesDeleted();
    }

    public void addToSelectedList(NoteObj noteObj) {
        selectedNotes.add(noteObj);
    }

    public void removeFromSelectedList(NoteObj noteObj) {
        selectedNotes.remove(noteObj);
    }

    public ArrayList<NoteObj> getSelectedNotes() {
        return selectedNotes;
    }

    public void clearSelectedList() {
        selectedNotes.clear();
    }
}
