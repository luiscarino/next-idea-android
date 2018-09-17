package com.luiscarino.nextidea.view.preferences

import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.luiscarino.nextidea.R

class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        setPreferencesFromResource(R.xml.preferences, null)
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference is MyDialogPreference) {
            val dialogFragment = MyPreferenceDialogFragment.nexInstance(preference.key)
            dialogFragment.setTargetFragment(this, 0)
            dialogFragment.show(fragmentManager, null)
        } else {
            super.onDisplayPreferenceDialog(preference)
        }
    }
}