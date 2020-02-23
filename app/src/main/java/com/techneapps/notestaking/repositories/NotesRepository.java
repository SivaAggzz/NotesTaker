package com.techneapps.notestaking.repositories;

import androidx.lifecycle.MutableLiveData;

import com.techneapps.notestaking.database.NotesDatabase;
import com.techneapps.notestaking.database.models.NoteObj;
import com.techneapps.notestaking.view.listener.OnNoteSavedListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotesRepository {
    //Data to provide to View
    private MutableLiveData<List<NoteObj>> noteObjects;
    //Data source
    private NotesDatabase notesDatabase;

    public NotesRepository(NotesDatabase notesDatabase) {
        this.notesDatabase = notesDatabase;
    }

    //method to return saved notes
    public MutableLiveData<List<NoteObj>> getNoteObjects() {
        if (noteObjects == null) {
            noteObjects = new MutableLiveData<>();
        }
        return getAllSavedNotesThread();
    }

    //method to get saved note from Room database by using RXJava
    private MutableLiveData<List<NoteObj>> getAllSavedNotesThread() {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> notesDatabase.getNotesDao().getNotes())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> noteObjects.setValue(result)));
        return noteObjects;
    }

    //method to delete all saved notes from Room database by using RXJava
    public void clearNotesObjects() {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> {
            notesDatabase.clearAllTables();
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                }));
    }

    //method to delete a saved note from Room database by using RXJava
    public void deleteNote(NoteObj noteObj) {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> {
            notesDatabase.getNotesDao().deleteNote(noteObj);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                }));
    }

    //method to save note to Room database by using RXJava
    public void updateNote(NoteObj noteObj) {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> {
            notesDatabase.getNotesDao().updateNote(noteObj);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {

                }));
    }

    //method to add note to Room database by using RXJava
    public void addNote(NoteObj noteObj, OnNoteSavedListener onNoteSavedListener) {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> {
            notesDatabase.getNotesDao().addNote(noteObj);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> onNoteSavedListener.OnNoteSaved()));
    }


}
