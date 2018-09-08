package com.luiscarino.nextidea.util.recyclerview

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Cannot be done in kotlin at the moment.
 *
 */
abstract class KBaseRecyclerViewAdapter<T : RecyclerViewType> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var delegateAdapters = SparseArrayCompat<KDelegateAdapter<*,*>>()
    protected var items = listOf<T>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegateAdapter = delegateAdapters.get(viewType)
        if (delegateAdapter != null) {
            return delegateAdapter.onCreateViewHolder(viewGroup)
        } else {
            throw IllegalAccessException("Invalid view type. Must be added to the list of DAs")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, viewType: Int) {
        val delegateAdapter = delegateAdapters.get(viewType)
        if (delegateAdapter != null) {
         //  delegateAdapter.onBindViewHolder(viewHolder, viewType)
        } else {
            throw IllegalAccessException("Invalid view type. Must be added to the list of DAs")
        }
    }


    override fun getItemViewType(position: Int): Int {
        return items[position].vieType
    }
}