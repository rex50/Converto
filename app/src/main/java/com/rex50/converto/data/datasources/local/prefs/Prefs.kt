package com.rex50.converto.data.datasources.local.prefs

import android.content.Context
import android.content.SharedPreferences

/**
 * Utility abstract class for accessing prefs with extension functions
 */
abstract class Prefs (
    context: Context,
    prefsName: String = "ConvertoPrefs"
) {
    private val pref: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    fun String.getLong(default: Long = 0) = pref.getLong(this, default)

    fun String.getString(default: String = "") = pref.getString(this, default)!!
}