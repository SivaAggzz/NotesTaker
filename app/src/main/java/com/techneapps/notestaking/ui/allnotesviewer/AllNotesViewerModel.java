package com.techneapps.notestaking.ui.allnotesviewer;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.data.dao.notes.NotesDatabase;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AllNotesViewerModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ArrayList<NoteObj>> noteObjects;
    private NotesDatabase notesDatabase;

    public AllNotesViewerModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        notesDatabase = Room.databaseBuilder(context, NotesDatabase.class, "notes.db").build();
    }


    MutableLiveData<ArrayList<NoteObj>> getNoteObjects() {
        if (noteObjects == null) {
            noteObjects = new MutableLiveData<>();
        }
        return getAllSavedNotesThread();
    }


    //method to get saved note from Room database by using RXJava
    private MutableLiveData<ArrayList<NoteObj>> getAllSavedNotesThread() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> notesDatabase.getNotesDao().getNotes())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> noteObjects.setValue(new ArrayList<>(result))));
        return noteObjects;
    }

    //method to clear saved note from Room database by using RXJava
    void clearNotesObjects() {
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

    void deleteNote(NoteObj noteObj) {
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
}
