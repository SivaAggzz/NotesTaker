package com.techneapps.notestaking.util.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.techneapps.notestaking.database.NotesDatabase;
import com.techneapps.notestaking.viewModel.NotesViewModel;

public class NotesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private NotesDatabase notesDatabase;

    public NotesViewModelFactory(NotesDatabase notesDatabase) {
        this.notesDatabase = notesDatabase;
    }


    @SuppressWarnings("unchecked")
    // This would be helpful for lint warnings for casts.
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == NotesViewModel.class) {
            return (T) new NotesViewModel(notesDatabase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}