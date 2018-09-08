package com.luiscarino.nextidea.util.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface DelegateAdapter<VH extends RecyclerView.ViewHolder, T extends RecyclerViewType> {

    VH onCreateViewHolder(ViewGroup viewGroup);

    void onBindViewHolder(VH viewHolder, T item);
}
