package com.luiscarino.nextidea.list.model.room.coverter

import android.arch.persistence.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return if (date == null) null else date.time
    }
}