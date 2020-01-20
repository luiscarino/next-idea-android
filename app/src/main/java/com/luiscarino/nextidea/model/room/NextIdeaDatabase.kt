package com.luiscarino.nextidea.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luiscarino.nextidea.model.room.coverter.DateConverter
import com.luiscarino.nextidea.model.room.dao.CategoryDao
import com.luiscarino.nextidea.model.room.dao.IdeaDao
import com.luiscarino.nextidea.model.room.dao.StatusDao
import com.luiscarino.nextidea.model.room.entity.Category
import com.luiscarino.nextidea.model.room.entity.Idea
import com.luiscarino.nextidea.model.room.entity.Status


@Database(entities = [Idea::class, Category::class, Status::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NextIdeaDatabase : RoomDatabase() {

    abstract fun ideaDao() : IdeaDao
    abstract fun statusDao() : StatusDao
    abstract fun categoryDao() : CategoryDao

}