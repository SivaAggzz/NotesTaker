package com.techneapps.notestaking.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.data.dao.notes.NoteObj;
import com.techneapps.notestaking.databinding.SingleListNoteBinding;
import com.techneapps.notestaking.ui.adapter.viewholder.NotesViewHolder;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private Context context;
    private ArrayList<NoteObj> noteObjs;

    public NotesAdapter(Context context, ArrayList<NoteObj> noteObjs) {
        this.context = context;
        this.noteObjs = noteObjs;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleListNoteBinding singleListNoteBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.single_list_note, parent, false);
        return new NotesViewHolder(singleListNoteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int position) {
        notesViewHolder.singleListNoteBinding.setNote(noteObjs.get(position));
    }

    @Override
    public int getItemCount() {
        return noteObjs.size();
    }
}
