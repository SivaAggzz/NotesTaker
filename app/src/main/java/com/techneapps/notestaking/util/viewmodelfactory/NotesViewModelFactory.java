package com.techneapps.notestaking.util.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.techneapps.notestaking.data.local.NotesRepo;
import com.techneapps.notestaking.viewmodel.NotesViewModel;

public class NotesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private NotesRepo notesDatabases;

    public NotesViewModelFactory(NotesRepo... notesRepos) {
        this.notesDatabases = notesRepos[0];
    }


    @SuppressWarnings("unchecked")
    // This would be helpful for lint warnings for casts.
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == NotesViewModel.class) {
            return (T) new NotesViewModel(notesDatabases);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}