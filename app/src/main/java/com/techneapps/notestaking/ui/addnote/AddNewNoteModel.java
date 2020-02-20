package com.techneapps.notestaking.ui.addnote;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.data.dao.notes.NotesDatabase;
import com.techneapps.notestaking.providers.interfaces.OnNoteSavedListener;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("SameReturnValue")
public class AddNewNoteModel extends AndroidViewModel {
    private Context context;
    private NotesDatabase notesDatabase;

    public AddNewNoteModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        notesDatabase = Room.databaseBuilder(context, NotesDatabase.class, "notes.db").build();
    }


    //method to save note to Room database by using RXJava
    void saveNote(NoteObj noteObj, OnNoteSavedListener onNoteSavedListener) {
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
