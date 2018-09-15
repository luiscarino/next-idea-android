package com.luiscarino.nextidea.model.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "status_table")
data class Status(
        var statusTitle: String = "NOT STARTED",
        @PrimaryKey(autoGenerate = true)
        var statusId: Long = 0
)