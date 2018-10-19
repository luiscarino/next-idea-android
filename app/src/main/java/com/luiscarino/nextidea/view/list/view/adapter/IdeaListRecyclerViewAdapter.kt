package com.luiscarino.nextidea.view.list.view.adapter

import io.luiscarino.abcrecyclerview.BaseRecyclerViewAdapter
import io.luiscarino.abcrecyclerview.RecyclerViewType

class IdeaListRecyclerViewAdapter(actions: IdeaListItemDelegateAdapter.Actions)
    : BaseRecyclerViewAdapter<RecyclerViewType>() {

    init {
        delegateAdapters.put(IdeaListItemDelegateAdapter.VIEW_TYPE, IdeaListItemDelegateAdapter(actions))
    }

    fun setIdeas(ideas: List<IdeaListItemRecyclerRecyclerViewType>) {
        items.clear()
        for (idea in ideas) items.add(idea)
        notifyDataSetChanged()
    }


}

