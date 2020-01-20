package com.luiscarino.nextidea

import android.app.Application
import com.luiscarino.nextidea.di.appModule
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin

class NextIdeaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))
        initLeakCanary()
    }

    private fun initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) return else LeakCanary.install(this)
        }
    }
}