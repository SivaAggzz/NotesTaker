package com.techneapps.notestaking.ui.adapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.notestaking.databinding.SingleListNoteBinding;

public class NotesViewHolder extends RecyclerView.ViewHolder {
    public SingleListNoteBinding singleListNoteBinding;
    //layout binding for view holder

    public NotesViewHolder(@NonNull SingleListNoteBinding singleListNoteBinding) {
        super(singleListNoteBinding.getRoot());
        this.singleListNoteBinding = singleListNoteBinding;
    }
}
