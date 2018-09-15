package com.luiscarino.nextidea.model.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.model.room.coverter.DateConverter
import com.luiscarino.nextidea.model.room.dao.CategoryDao
import com.luiscarino.nextidea.model.room.dao.IdeaDao
import com.luiscarino.nextidea.model.room.dao.StatusDao
import com.luiscarino.nextidea.model.room.entity.Category
import com.luiscarino.nextidea.model.room.entity.Idea
import com.luiscarino.nextidea.model.room.entity.Status
import com.luiscarino.nextidea.util.PopulateDbAsync


@Database(entities = [Idea::class, Category::class, Status::class], version = 1)
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
                            NextIdeaDatabase::class.java, context.getString(R.string.database_name))
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

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }

    }


}


