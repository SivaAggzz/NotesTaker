package com.techneapps.notestaking.util.selectableadapter;


import android.util.SparseBooleanArray;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectableAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private SparseBooleanArray selecteditems;

    protected SelectableAdapter() {
        selecteditems = new SparseBooleanArray();
    }


    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void toggleSelection(int pos) {
        if (selecteditems.get(pos, false)) {
            selecteditems.delete(pos);
        } else {
            selecteditems.put(pos, true);

        }
        notifyDataSetChanged();
    }

    public void toggleAllSelection(int count) {
        for (int pos = 0; pos < count; pos++) {
            if (selecteditems.get(pos, false)) {
                selecteditems.delete(pos);
            } else {
                selecteditems.put(pos, true);

            }
            notifyItemChanged(pos);
        }
    }

    public void selectAllitems(int count) {
        for (int i = 0; i < count; i++) {
            selecteditems.delete(i);
            selecteditems.put(i, true);
            notifyItemChanged(i);

        }
    }

    public void unselectAllitems(int count) {

        for (int i = 0; i < count; i++) {
            selecteditems.delete(i);
            selecteditems.put(i, false);
            notifyItemChanged(i);
        }

    }


    public void clearSelection() {
        selecteditems.clear();
        notifyDataSetChanged();
    }


    public int getSelectedItemCount() {
        return selecteditems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selecteditems.size());
        for (int i = 0; i < selecteditems.size(); i++) {
            items.add(selecteditems.keyAt(i));
        }
        return items;
    }


}