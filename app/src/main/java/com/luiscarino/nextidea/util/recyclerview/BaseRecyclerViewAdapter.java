package com.luiscarino.nextidea.util.recyclerview;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class BaseRecyclerViewAdapter<T extends RecyclerViewType> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected SparseArrayCompat<DelegateAdapter> delegateAdapters;
    protected ArrayList<T> items;

    public BaseRecyclerViewAdapter() {
        this.delegateAdapters = new SparseArrayCompat<>();
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        DelegateAdapter delegateAdapter = delegateAdapters.get(items.get(position).getVieType());
        if (delegateAdapter == null) {
            throw new IllegalArgumentException("Not DA for item "+position);
        } else {
            return delegateAdapter.onCreateViewHolder(parent);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        DelegateAdapter delegateAdapter = delegateAdapters.get(items.get(position).getVieType());
        if (delegateAdapter == null) {
            throw new IllegalArgumentException("No DA for item " + position);
        } else {
            delegateAdapter.onBindViewHolder(viewHolder, items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
