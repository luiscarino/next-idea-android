package com.luiscarino.nextidea.list.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.util.ConstantsDelegateAdapter
import com.luiscarino.nextidea.util.recyclerview.DelegateAdapter
import com.luiscarino.nextidea.util.toButtonColor
import com.luiscarino.nextidea.util.toDrawableId

class IdeaListItemDelegateAdapter(val actions:  Actions) : DelegateAdapter
<IdeaListItemDelegateAdapter.IdeaItemViewHolder, IdeaListItemRecyclerRecyclerViewType> {

    companion object {
        const val VIEW_TYPE = ConstantsDelegateAdapter.IDEA_LIST_ITEM_VIEW_TYPE
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?): IdeaItemViewHolder {
        return IdeaItemViewHolder(
                LayoutInflater.from(viewGroup?.context).inflate(R.layout.recyclerview_idea_item, viewGroup, false),
                actions)
    }

    override fun onBindViewHolder(viewHolder: IdeaItemViewHolder?, item: IdeaListItemRecyclerRecyclerViewType?) {
        viewHolder?.onBind(item)
    }

    class IdeaItemViewHolder(itemView: View, val actions: Actions) : RecyclerView.ViewHolder(itemView) {

        private var titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private var iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        private var categoryNameTextView: TextView = itemView.findViewById(R.id.categoryName)
        private var lastEditTextView: TextView = itemView.findViewById(R.id.lastEditTextView)
        private var statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        private var formatterLastEdit = itemView.context.getString(R.string.formatter_item_last_edit)

        fun onBind(item: IdeaListItemRecyclerRecyclerViewType?) {
            titleTextView.text = item?.title
            val categoryName = item?.categoryName
            iconImageView.setImageDrawable(iconImageView.context.getDrawable(toDrawableId(categoryName)))
            categoryNameTextView.text = categoryName
            lastEditTextView.text = String.format(formatterLastEdit, item?.lastEdited)
            val statusName = item?.statusName
            statusTextView.text = statusName
            statusTextView.setBackgroundColor(statusTextView.resources.getColor(toButtonColor(statusName)))
            itemView.setOnClickListener { actions.onCardClicked(item?.id) }
        }

    }

    interface Actions {
        fun onCardClicked(id:Long?)
    }

}