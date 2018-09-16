package com.luiscarino.nextidea.ratemyapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast

class RateMyApp(private val activity: AppCompatActivity,
                private val configuration: RateMyAppConfig) {

    private val sharedPreferences: SharedPreferences = activity.getSharedPreferences(PreferencesContract.SHARED_PREFERENCES_NAME, 0)


    companion object {
        private const val TAG = "RateMyApp"
    }

    // Display dialog if required
    fun init() {

        Log.d(TAG, "Init RateMyApp")

        // check if not required
        if (sharedPreferences.getBoolean(PreferencesContract.PREF_DONT_SHOW_AGAIN, false)
                || sharedPreferences.getBoolean(PreferencesContract.PREF_APP_HAS_CRASHED, false)
                || !configuration.showIfHasCrashed) {
            return
        }

        if (!configuration.showIfHasCrashed) {
            // TODO
        }

        val editor = sharedPreferences.edit()

        // get launch counter  and increment by one
        val launchCount = sharedPreferences.getLong(PreferencesContract.PREF_LAUNCH_COUNT, 0).plus(1)
        editor.putLong(PreferencesContract.PREF_LAUNCH_COUNT, launchCount)

        // get date of first launch
        var dateFirstLaunched = sharedPreferences.getLong(PreferencesContract.PREF_DATE_FIRST_LAUNCH, 0)
        if (dateFirstLaunched == 0L) {
            dateFirstLaunched = System.currentTimeMillis()
            editor.putLong(PreferencesContract.PREF_DATE_FIRST_LAUNCH, dateFirstLaunched)
        }

        // check if dialog is needed
        if (launchCount >= configuration.minimumLaunchesUntilPrompt) {
            // check if current time is more or equal thant date first launched + configured min days until prompt
            if (System.currentTimeMillis()
                    >= dateFirstLaunched.plus(configuration.minimumDaysUntilPrompt * DateUtils.DAY_IN_MILLIS)) {

                if (configuration.customDialogBuilder == null) {
                    displayDefaultDialog()
                } else {
                    displayCustomDialog()
                }
            }
        }

        editor.apply()
    }

    private fun displayCustomDialog() {
        // TODO: add click listeners
        configuration.customDialogBuilder?.show()
    }

    private fun displayDefaultDialog() {
        // TODO: move to string resources when moved to library
        Log.d(TAG, "Create default dialog.")

        val title = String.format("Rate %s", getApplicationName(activity.applicationContext))
        val message = String.format("If you enjoy using %s, please take a moment to rate it. Thanks for your support!",
                getApplicationName(activity.applicationContext))
        val rate = "Rate it!"
        val remindLater = "Remind me later"
        val dismiss = "No thanks"


        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(rate) { _, _ ->
            run {
                launchPlayStore()
            }
        }
        builder.show()
    }

    private fun launchPlayStore() {
        try {
            activity.startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + activity.packageName)))
        } catch (ignored: ActivityNotFoundException) {
            Toast.makeText(activity, "No Play Store installed on device", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Rate my app configuration object.
     * @param minimumLaunchesUntilPrompt The minimum number of times the app is launched before
     * showing the rate dialog. Default value is 0.
     *
     * @param minimumDaysUntilPrompt  The minimum number of days before
     * showing the rate dialog. Default value is 0.
     *
     * @param showIfHasCrashed Indicator that determines if dialog has to show if app has crashed.
     * Default value is true.
     *
     * @param customDialogBuilder Custom dialog. Default is null
     *
     */
    class RateMyAppConfig(val minimumLaunchesUntilPrompt: Long = 0,
                          val minimumDaysUntilPrompt: Long = 0,
                          val showIfHasCrashed: Boolean = true,
                          val customDialogBuilder: AlertDialog.Builder? = null)
}