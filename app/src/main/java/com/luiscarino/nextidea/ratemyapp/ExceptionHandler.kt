package com.luiscarino.nextidea.ratemyapp

import android.content.Context

class ExceptionHandler
internal constructor(private val defaultExceptionHandler: Thread.UncaughtExceptionHandler, context: Context)
    : Thread.UncaughtExceptionHandler {

    private val preferences = context.getSharedPreferences(PreferencesContract.SHARED_PREFERENCES_NAME, 0)

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        preferences.edit().putBoolean(PreferencesContract.PREF_APP_HAS_CRASHED, true).apply()
        defaultExceptionHandler.uncaughtException(t, e)
    }
}