package com.medok.app.controller

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.medok.app.receivers.NotificationReceiver
import java.util.Calendar

@SuppressLint("ScheduleExactAlarm")
fun scheduleNotification(
    context: Context,
    hour: Int,
    minute: Int,
    date: String,
    name: String,
    pathologies: Map<String, Boolean>,
    medicines: Map<String, Boolean>,
    recurrenceActive: Boolean,
    recurrenceDateType: String,
    everyRecurrence: String,
    selectedRadio : String,
    endDate: String?,
    endTimes: String?,
    reminderId: Int
) {
    val calendar = Calendar.getInstance()
    val dateParts = date.split("/")
    if (dateParts.size == 3) {
        try {
            val day = dateParts[0].toInt()
            val month = dateParts[1].toInt() - 1
            val year = dateParts[2].toInt()
            calendar.set(year, month, day, hour, minute, 0)
        } catch (e: NumberFormatException) {
            println("Erreur de formatage de la date : $date")
        }
    } else {
        println("Format de date invalide : $date")
    }
    calendar.set(Calendar.MILLISECOND, 0)
    if (calendar.timeInMillis > System.currentTimeMillis()) {
        val selectedPathologies = pathologies.filter { it.value }.keys.joinToString(", ")
        val selectedMedicines = medicines.filter { it.value }.keys.joinToString(", ")
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("REMINDER_NAME", name)
            putExtra("REMINDER_PATHOLOGIES", selectedPathologies)
            putExtra("REMINDER_MEDICINES", selectedMedicines)
            putExtra("REMINDER_ID", reminderId)
            putExtra("RECURRENCE_ACTIVE", recurrenceActive)
            putExtra("RECURRENCE_DATE_TYPE", recurrenceDateType)
            putExtra("EVERY_RECURRENCE", everyRecurrence)
            putExtra("SELECTED_RADIO", selectedRadio)
            putExtra("END_DATE", endDate)
            putExtra("END_TIMES", endTimes)
            putExtra("DATE_FOR_SCHEDULE", date)
            putExtra("HOUR_FOR_SCHEDULE", hour)
            putExtra("MINUTE_FOR_SCHEDULE", minute)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    } else {
        println("La date/heure de notification est dans le passé, la notification ne sera pas planifiée.")
    }
}

@SuppressLint("UnspecifiedImmutableFlag")
fun cancelNotification(context: Context, rappelId: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        rappelId,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
}