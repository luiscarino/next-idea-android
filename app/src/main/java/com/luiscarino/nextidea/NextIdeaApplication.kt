package com.luiscarino.nextidea

import android.app.Application
import android.content.SharedPreferences
import com.luiscarino.nextidea.di.appModule
import com.luiscarino.nextidea.model.room.NextIdeaDatabase
import com.luiscarino.nextidea.ratemyapp.PreferencesContract
import com.luiscarino.nextidea.util.populateDatabase
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class NextIdeaApplication : Application() {

    private val database: NextIdeaDatabase by inject()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin(this, listOf(appModule))
        // TODO: Moved this out of callback in favor of DI. Check later how to properly implement using callback
        initDatabaseIfRequired()
    }

    private fun initDatabaseIfRequired() {
        if (sharedPreferences.getBoolean(PreferencesContract.PREF_IS_FIRST_LAUNCH, true)) {
            populateDatabase(database)
            sharedPreferences.edit().putBoolean(PreferencesContract.PREF_IS_FIRST_LAUNCH, false).apply()
        }
    }



}