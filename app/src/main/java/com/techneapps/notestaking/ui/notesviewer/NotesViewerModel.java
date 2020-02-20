package com.techneapps.notestaking.ui.notesviewer;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.techneapps.notestaking.data.NoteObj;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotesViewerModel extends AndroidViewModel {
    public ArrayList<NoteObj> getNotes
    private Context context;
    private MutableLiveData<ArrayList<NoteObj>> noteObjects;

    {
        if (noteObjects == null) {
            noteObjects = new ArrayList<>();
        }
    }

    public NotesViewerModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    private MutableLiveData<ArrayList<NoteObj>> getAllSavedNotesThread(File dirPath) {


        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(Observable.fromCallable(() -> {
            return noteObjects;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) ->));
        return noteObjects;
    }

}
