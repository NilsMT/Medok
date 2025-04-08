package com.medok.app.data

import android.content.Context
import com.example.applicationsae.model.MedicalReminderModele
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun saveReminders(context: Context, rappels: List<MedicalReminderModele>) {
    if (rappels.any { it.hour == "invalid hour" || it.date == "invalid date" || it.endDate == "invalid endDate" }) {
        throw IllegalArgumentException("Invalid reminder data")
    }
    val sharedPreferences = context.getSharedPreferences("RappelsPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val json = gson.toJson(rappels)
    editor.putString("rappels", json)
    editor.apply()
}


fun loadReminders(context: Context): List<MedicalReminderModele> {
    val sharedPreferences = context.getSharedPreferences("RappelsPrefs", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("rappels", "[]")
    val gson = Gson()
    val listType = object : TypeToken<List<MedicalReminderModele>>() {}.type
    return gson.fromJson(json, listType)
}
