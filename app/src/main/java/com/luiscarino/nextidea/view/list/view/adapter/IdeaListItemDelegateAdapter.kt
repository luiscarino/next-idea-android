package com.luiscarino.nextidea.view.list.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.util.ConstantsDelegateAdapter
import com.luiscarino.nextidea.util.recyclerview.DelegateAdapter
import com.luiscarino.nextidea.util.toButtonColor
import com.luiscarino.nextidea.util.toDrawableId
import kotlinx.android.synthetic.main.recyclerview_idea_item.view.*

/**
 * Encapsulates how to display a list item.
 */
class IdeaListItemDelegateAdapter(val actions: Actions) : DelegateAdapter
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

        private var formatterLastEdit = itemView.context.getString(R.string.formatter_item_last_edit)

        fun onBind(item: IdeaListItemRecyclerRecyclerViewType?) {
            itemView.titleTextView.text = item?.title

            itemView.iconImageView.setImageDrawable(itemView.context.getDrawable(toDrawableId(item?.categoryName)))

            itemView.categoryName.text = item?.categoryName

            itemView.lastEditTextView.text = String.format(formatterLastEdit, item?.lastEdited)

            val statusName = item?.statusName
            itemView.statusTextView.text = statusName
            itemView.statusTextView.setBackgroundColor(itemView.resources.getColor(toButtonColor(statusName)))

            itemView.setOnClickListener { actions.onCardClicked(item?.id) }
        }

    }

    interface Actions {
        fun onCardClicked(id: Long?)
    }

}