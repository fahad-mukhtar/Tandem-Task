package com.example.community.Utilities

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Prefs(context: Context) {

    companion object {
        private const val PREFS_FILENAME = "tandem_prefs"
        private const val KEY_LIKED_INDEX = "liked_indexes"
    }

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var likedIndexes: String
        get() = sharedPrefs.getString(KEY_LIKED_INDEX, "") ?: ""
        set(value) = sharedPrefs.edit { putString(KEY_LIKED_INDEX, value) }

}