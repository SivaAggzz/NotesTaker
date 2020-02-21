package com.techneapps.notestaking.ui.editnote;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.data.dao.notes.NotesDatabase;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("SameReturnValue")
public class EditNoteModel extends AndroidViewModel {
    private Context context;
    private NotesDatabase notesDatabase;

    public EditNoteModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        notesDatabase = Room.databaseBuilder(context, NotesDatabase.class, "notes.db").build();
    }


    //method to save note to Room database by using RXJava
    void updateNote(NoteObj noteObj) {
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
}
