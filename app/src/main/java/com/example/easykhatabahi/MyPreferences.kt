package com.example.easykhatabahi

import android.content.Context
import android.preference.PreferenceManager

class MyPreferences(context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferences.getInt(DARK_STATUS, ZERO)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()

    companion object {
        private const val DARK_STATUS = "darkStatus"
        private const val ZERO = 0
    }
}