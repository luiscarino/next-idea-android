package com.luiscarino.nextidea.di

import android.arch.persistence.room.Room
import com.luiscarino.nextidea.model.room.NextIdeaDatabase
import org.koin.dsl.module.module

val appTestModule = module {

    // override database definition
    single(override = true) {
        // override for this definition
        // In-Memory database config
        Room.inMemoryDatabaseBuilder(get(), NextIdeaDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }
}