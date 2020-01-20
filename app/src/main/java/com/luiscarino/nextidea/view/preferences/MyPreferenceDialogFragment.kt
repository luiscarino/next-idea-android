package com.luiscarino.nextidea.view.preferences

import android.os.Bundle
import androidx.preference.PreferenceDialogFragmentCompat

class MyPreferenceDialogFragment : PreferenceDialogFragmentCompat() {

    companion object {
        fun nexInstance(key: String): MyPreferenceDialogFragment {
            val myPreferenceDialogFragment = MyPreferenceDialogFragment()
            myPreferenceDialogFragment.arguments = Bundle().apply { putString("key", key) }
            return myPreferenceDialogFragment
        }
    }

    override fun onDialogClosed(p0: Boolean) {
    }
}