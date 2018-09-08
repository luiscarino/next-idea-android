package com.luiscarino.nextidea.util.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Interface
 */
interface KDelegateAdapter<VH : RecyclerView.ViewHolder, T : RecyclerViewType> {

    fun onCreateViewHolder(parent: ViewGroup): VH

    fun onBindViewHolder(holder: VH, item: T)
}
