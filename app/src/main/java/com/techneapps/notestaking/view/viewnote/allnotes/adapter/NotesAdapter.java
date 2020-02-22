package com.techneapps.notestaking.view.viewnote.allnotes.adapter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.techneapps.notestaking.R;
import com.techneapps.notestaking.data.NoteObj;
import com.techneapps.notestaking.databinding.SingleListNoteBinding;
import com.techneapps.notestaking.util.selectableadapter.SelectableAdapter;
import com.techneapps.notestaking.view.viewnote.allnotes.AllNotesViewerActivity;
import com.techneapps.notestaking.view.viewnote.allnotes.adapter.clickHandler.SingleNoteClickHandler;
import com.techneapps.notestaking.view.viewnote.allnotes.adapter.viewholder.NotesViewHolder;

import java.util.ArrayList;

public class NotesAdapter extends SelectableAdapter<NotesViewHolder> {
    private final TypedValue outValue;
    private ArrayList<NoteObj> noteObjs;
    private AllNotesViewerActivity allNotesViewerActivity;

    public NotesAdapter(AllNotesViewerActivity allNotesViewerActivity, ArrayList<NoteObj> noteObjs) {
        this.allNotesViewerActivity = allNotesViewerActivity;
        this.noteObjs = noteObjs;
        setHasStableIds(true);
        outValue = new TypedValue();
        allNotesViewerActivity.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleListNoteBinding singleListNoteBinding = DataBindingUtil
                .inflate(LayoutInflater.from(allNotesViewerActivity), R.layout.single_list_note, parent, false);
        return new NotesViewHolder(singleListNoteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int position) {
        if (getSelectedItems().contains(position)) {
            notesViewHolder.singleListNoteBinding.getRoot().setBackgroundColor(Color.parseColor("#012131"));
        } else {
            notesViewHolder.singleListNoteBinding.getRoot().setBackgroundResource(outValue.resourceId);
        }
        notesViewHolder.singleListNoteBinding.setNote(noteObjs.get(position));
        notesViewHolder.singleListNoteBinding.setClickHandler(new SingleNoteClickHandler(allNotesViewerActivity, position));


    }

    public void removeItem(int position) {
        noteObjs.remove(position);
        notifyItemRemoved(position);
    }
    public NoteObj get(int position) {
        return noteObjs.get(position);
    }
    @Override
    public int getItemCount() {
        return noteObjs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
