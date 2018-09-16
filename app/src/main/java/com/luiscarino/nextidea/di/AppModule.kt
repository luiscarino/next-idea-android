package com.luiscarino.nextidea.di

import android.arch.persistence.room.Room
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.model.room.NextIdeaDatabase
import com.luiscarino.nextidea.model.room.repository.IdeaRepository
import com.luiscarino.nextidea.ratemyapp.PreferencesContract
import com.luiscarino.nextidea.viewmodel.IdeaViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Create Koin module for Dependency Injection
 */
val appModule = module {

    // Shared preferences
    single {
        androidApplication().getSharedPreferences(PreferencesContract.SHARED_PREFERENCES_NAME, 0)
    }

    // ROOM Database instance
    single {
        Room.databaseBuilder(androidApplication(),
                NextIdeaDatabase::class.java, androidApplication().getString(R.string.database_name))
                .build()
    }

    // ROOM Idea DAO instance
    single {
        get<NextIdeaDatabase>().ideaDao()
    }

    // ROOM Category DAO instance
    single {
        get<NextIdeaDatabase>().categoryDao()
    }

    // ROOM StatusDao DAO instance
    single {
        get<NextIdeaDatabase>().statusDao()
    }

    // Repositories
    single {
        IdeaRepository(get(), get(), get())
    }

    viewModel { IdeaViewModel(get(), get()) }

}