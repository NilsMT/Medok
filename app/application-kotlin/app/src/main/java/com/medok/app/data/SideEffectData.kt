package com.medok.app.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.medok.app.model.SideEffectModele

fun saveSideEffects(context: Context, sideEffects: List<SideEffectModele>) {
    try {
        val gson = Gson()
        val json = gson.toJson(sideEffects)
        if (sideEffects.any { it.name.isBlank() || it.date.isBlank() }) {
            throw Exception("Corrupted side effect data detected.")
        }
        val sharedPreferences = context.getSharedPreferences("SideEffectsPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("sideEffects", json)
        editor.apply()
    } catch (e: Exception) {
        throw Exception("Error saving side effects: ${e.message}")
    }
}


fun loadSideEffects(context: Context): List<SideEffectModele> {
    val sharedPreferences = context.getSharedPreferences("SideEffectsPrefs", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("sideEffects", "[]")
    val gson = Gson()
    val listType = object : TypeToken<List<SideEffectModele>>() {}.type
    return gson.fromJson(json, listType)
}
