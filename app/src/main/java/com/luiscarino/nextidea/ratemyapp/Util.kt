package com.luiscarino.nextidea.ratemyapp

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

/**
 * @param context A context of the current application.
 * *
 * @return The application name of the current application.
 */
fun getApplicationName(context: Context): String {
    val packageManager = context.packageManager
    val applicationInfo: ApplicationInfo?
    applicationInfo = try {
        packageManager.getApplicationInfo(context.packageName, 0)
    } catch (ignored: PackageManager.NameNotFoundException) {
        null
    }

    return (if (applicationInfo != null)
        packageManager.getApplicationLabel(applicationInfo) else "(unknown)") as String
}