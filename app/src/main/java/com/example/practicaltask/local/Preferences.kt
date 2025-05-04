package com.example.practicaltask.local

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {

    val PREFERENCE_NAME = "TaskPref"
    val hasToRemember = "hasToRemember"
    val userInfo = "userInfo"

    fun getPreference(): SharedPreferences? {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = getPreference()?.edit()

        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun getBoolean(key: String): Boolean? {
        return getPreference()?.getBoolean(key, false)
    }

    fun putString(key: String, value: String) {
        val editor = getPreference()?.edit()

        editor?.putString(key, value)
        editor?.apply()
    }

    fun getString(key: String): String? {
        return getPreference()?.getString(key, "")
    }

    fun clear() {
        val editor = getPreference()?.edit()
        editor?.clear()
        editor?.apply()
    }
}