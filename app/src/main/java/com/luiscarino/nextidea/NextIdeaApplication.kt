package com.luiscarino.nextidea

import android.app.Application
import com.luiscarino.nextidea.di.appModule
import org.koin.android.ext.android.startKoin

class NextIdeaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin(listOf(appModule))
    }

}