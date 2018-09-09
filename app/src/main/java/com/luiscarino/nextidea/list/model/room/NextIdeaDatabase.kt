package com.luiscarino.nextidea.list.model.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.os.AsyncTask
import com.luiscarino.nextidea.list.model.room.coverter.DateConverter
import com.luiscarino.nextidea.list.model.room.dao.CategoryDao
import com.luiscarino.nextidea.list.model.room.dao.IdeaDao
import com.luiscarino.nextidea.list.model.room.dao.StatusDao
import com.luiscarino.nextidea.list.model.room.entity.Category
import com.luiscarino.nextidea.list.model.room.entity.Idea
import com.luiscarino.nextidea.list.model.room.entity.Status
import com.luiscarino.nextidea.util.PopulateDbAsync


@Database(entities = [Idea::class, Category::class, Status::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NextIdeaDatabase : RoomDatabase() {

    abstract fun ideaDao() : IdeaDao
    abstract fun statusDao() : StatusDao
    abstract fun categoryDao() : CategoryDao

    companion   object {
        private var INSTANCE: NextIdeaDatabase? = null

        fun getInstance(context: Context): NextIdeaDatabase? {
            if (INSTANCE == null) {
                synchronized(NextIdeaDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            NextIdeaDatabase::class.java, "nextidea.db")
                            .addCallback(roomDatabaseCallback)
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        private val roomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }

    }


}


