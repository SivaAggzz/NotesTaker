package com.techneapps.notestaking.ui.notesviewer;

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

public class NotesViewerModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ArrayList<NoteObj>> noteObjects;
    private NotesDatabase notesDatabase;

    public NotesViewerModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<ArrayList<NoteObj>> getNoteObjects() {
        if (noteObjects == null) {
            noteObjects = new MutableLiveData<>();
        }
        return getAllSavedNotesThread();
    }


    private MutableLiveData<ArrayList<NoteObj>> getAllSavedNotesThread() {
        notesDatabase = Room.databaseBuilder(context, NotesDatabase.class, "notes.db").build();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> notesDatabase.getNotesDao().getNotes())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> noteObjects.setValue(new ArrayList<>(result))));
        return noteObjects;
    }

}
