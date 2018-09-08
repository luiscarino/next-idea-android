package com.luiscarino.nextidea.list.model.room.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "idea_table")
data class Idea(
        var title: String,
        var description: String,

        @ForeignKey(entity = Status::class, parentColumns = ["id"],
                childColumns = ["statusId"])

        @Embedded
        var status: Status = Status(),

        @ForeignKey(entity = Category::class, parentColumns = ["id"],
                childColumns = ["categoryId"])
        @Embedded
        var category: Category = Category(),

        var lastUpdatedDate: Date = Calendar.getInstance().time,

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0
)
