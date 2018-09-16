package com.luiscarino.nextidea

import android.app.Application
import android.content.SharedPreferences
import com.luiscarino.nextidea.di.appModule
import com.luiscarino.nextidea.model.room.DefaultValues
import com.luiscarino.nextidea.model.room.NextIdeaDatabase
import com.luiscarino.nextidea.model.room.entity.Category
import com.luiscarino.nextidea.model.room.entity.Status
import com.luiscarino.nextidea.ratemyapp.PreferencesContract
import kotlinx.coroutines.experimental.launch
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
            populateDatabase()
            sharedPreferences.edit().putBoolean(PreferencesContract.PREF_IS_FIRST_LAUNCH, false).apply()
        }
    }

    private fun populateDatabase() {
        launch {
            val categoryDao = database.categoryDao()
            categoryDao.deleteAll()
            categoryDao.insert(Category(DefaultValues.CATEGORY_OTHER, "ic_twotone_other"))
            categoryDao.insert(Category(DefaultValues.CATEGORY_ART, "ic_twotone_art"))
            categoryDao.insert(Category(DefaultValues.CATEGORY_BUSINESS, "ic_twotone_business"))
            categoryDao.insert(Category(DefaultValues.CATEGORY_FOOD, "ic_twotone_food"))
            categoryDao.insert(Category(DefaultValues.CATEGORY_GAMING, "ic_twotone_games"))
            categoryDao.insert(Category(DefaultValues.CATEGORY_MUSIC, "ic_twotone_music"))
            categoryDao.insert(Category(DefaultValues.CATEGORY_PHOTOGRAPHY, "ic_twotone_photography"))
            categoryDao.insert(Category(DefaultValues.CATEGORY_PRODUCT, "ic_twotone_product"))
            categoryDao.insert(Category(DefaultValues.CATEGORY_TECH, "ic_twotone_tech"))
            categoryDao.insert(Category(DefaultValues.CATEGORY_WRITING, "ic_twotone_writing"))

            val statusDao = database.statusDao()
            statusDao.deleteAll()
            statusDao.insert(Status(DefaultValues.STATUS_NOT_STARTED))
            statusDao.insert(Status(DefaultValues.STATUS_IN_PROGRESS))
            statusDao.insert(Status(DefaultValues.STATUS_DONE))
            statusDao.insert(Status(DefaultValues.STATUS_BLOCKED))
        }

    }

}