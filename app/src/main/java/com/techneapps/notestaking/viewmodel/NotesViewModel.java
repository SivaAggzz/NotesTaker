package com.techneapps.notestaking.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techneapps.notestaking.data.NoteObj;
import com.techneapps.notestaking.data.local.NotesRepo;
import com.techneapps.notestaking.view.addnote.OnNoteSavedListener;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotesViewModel extends ViewModel {
    //Data to provide to View
    private MutableLiveData<ArrayList<NoteObj>> noteObjects;
    //Our Repo is NotesRepo
    private NotesRepo notesRepo;


    public NotesViewModel(NotesRepo notesRepo) {
        this.notesRepo = notesRepo;
    }

    public MutableLiveData<ArrayList<NoteObj>> getNoteObjects() {
        if (noteObjects == null) {
            noteObjects = new MutableLiveData<>();
            return getAllSavedNotesThread();
        }
        return noteObjects;
    }

    //method to get saved note from Room database by using RXJava
    private MutableLiveData<ArrayList<NoteObj>> getAllSavedNotesThread() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> notesRepo.getNotesDao().getNotes())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> noteObjects.setValue(new ArrayList<>(result))));
        return noteObjects;
    }

    //method to delete all saved notes from Room database by using RXJava
    public void clearNotesObjects() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> {
            notesRepo.clearAllTables();
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
            notesRepo.getNotesDao().deleteNote(noteObj);
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
            notesRepo.getNotesDao().updateNote(noteObj);
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
            notesRepo.getNotesDao().addNote(noteObj);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> onNoteSavedListener.OnNoteSaved()));
    }
}
