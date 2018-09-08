package com.luiscarino.nextidea.list.view.adapter

import com.luiscarino.nextidea.util.ConstantsDelegateAdapter
import com.luiscarino.nextidea.util.recyclerview.RecyclerViewType

class IdeaListItemRecyclerRecyclerViewType(val id:Long,
                                           val title :String,
                                           val description: String,
                                           val categoryId: Long,
                                           val categoryName:String,
                                           val lastEdited:String,
                                           val statusId: Long,
                                           val statusName: String) : RecyclerViewType {

    override val vieType: Int
        get() = ConstantsDelegateAdapter.IDEA_LIST_ITEM_VIEW_TYPE
}