package com.luiscarino.nextidea.util

import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.model.room.DefaultValues

fun toButtonColor(statusName: String?): Int {
    return when (statusName) {
        DefaultValues.STATUS_IN_PROGRESS -> R.color.bg_status_in_progress
        DefaultValues.STATUS_BLOCKED -> R.color.bg_status_blocked
        DefaultValues.STATUS_NOT_STARTED -> R.color.bg_status_not_started
        DefaultValues.STATUS_DONE -> R.color.bg_status_done
        else -> R.color.bg_status_not_started
    }
}

fun toDrawableId(categoryName: String?): Int {
    return when (categoryName) {
        DefaultValues.CATEGORY_OTHER -> R.drawable.ic_twotone_other
        DefaultValues.CATEGORY_BUSINESS -> R.drawable.ic_twotone_business
        DefaultValues.CATEGORY_FOOD -> R.drawable.ic_twotone_food
        DefaultValues.CATEGORY_GAMING -> R.drawable.ic_twotone_games
        DefaultValues.CATEGORY_MUSIC -> R.drawable.ic_twotone_music
        DefaultValues.CATEGORY_PHOTOGRAPHY -> R.drawable.ic_twotone_photography
        DefaultValues.CATEGORY_PRODUCT -> R.drawable.ic_twotone_product
        DefaultValues.CATEGORY_TECH -> R.drawable.ic_twotone_tech
        DefaultValues.CATEGORY_WRITING -> R.drawable.ic_twotone_writing
        DefaultValues.CATEGORY_ART -> R.drawable.ic_twotone_art
        else -> R.drawable.ic_twotone_other
    }
}