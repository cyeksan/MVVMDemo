package com.example.mvvmdemo.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT = "key_saved_at"
class PreferenceProvider(context: Context) {

    private val appContext: Context = context.applicationContext
    private val sharedPreferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveLastSavedAt(savedAt: String?) {
        sharedPreferences.edit().putString(KEY_SAVED_AT, savedAt).apply()
    }

    fun getLastSavedAt() : String? {
        return sharedPreferences.getString(KEY_SAVED_AT, null)
    }
}