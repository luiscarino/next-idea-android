package com.luiscarino.nextidea.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category (
        var categoryTitle : String = "Other",
        var iconId : String = "ic_twotone_other",
        @PrimaryKey(autoGenerate = true)
        var categoryId: Long = 0
)