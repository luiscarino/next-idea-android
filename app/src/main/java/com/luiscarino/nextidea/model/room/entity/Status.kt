package com.luiscarino.nextidea.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "status_table")
data class Status(
        var statusTitle: String = "NOT STARTED",
        @PrimaryKey(autoGenerate = true)
        var statusId: Long = 0
)