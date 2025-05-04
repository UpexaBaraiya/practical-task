package com.example.practicaltask.utils

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Any.toJson(): String {
    val gson = Gson()
    return gson.toJson(this)
}

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)

