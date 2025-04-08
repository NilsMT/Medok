package com.medok.app.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.medok.app.R
import com.medok.app.controller.scheduleNotification
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotificationReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val rappelName = intent.getStringExtra("REMINDER_NAME") ?: "Rappel Médical"
        val medicines = intent.getStringExtra("REMINDER_MEDICINES") ?: "Pas de médicaments"
        val pathologies = intent.getStringExtra("REMINDER_PATHOLOGIES") ?: "Pas de maladies"
        val recurrenceActive = intent.getBooleanExtra("RECURRENCE_ACTIVE", false)
        val rappelId = intent.getIntExtra("REMINDER_ID", -1)
        val recurrenceDateType = intent.getStringExtra("RECURRENCE_DATE_TYPE")
        val recurrenceEveryRecurrence = intent.getStringExtra("EVERY_RECURRENCE")
        showNotification(context, rappelName, rappelId, medicines, pathologies)

        if (recurrenceActive && recurrenceDateType != null && recurrenceEveryRecurrence != null){
            val recurrenceSelectedRadio = intent.getStringExtra("SELECTED_RADIO") ?: "Pas de fin de reccurence selectionnee"
            val recurrenceEndDate = intent.getStringExtra("END_DATE")
            val recurrenceEndTimes = intent.getStringExtra("END_TIMES")
            var hour = intent.getIntExtra("HOUR_FOR_SCHEDULE",0)
            var minute = intent.getIntExtra("MINUTE_FOR_SCHEDULE",0)
            var date = intent.getStringExtra("DATE_FOR_SCHEDULE") ?: "Pas de date"
            val mapPathologies = pathologies.split(", ").associateWith { true }
            val mapMedicines = medicines.split(", ").associateWith { true }
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            val normalizedDate = date.split("/")
                .joinToString("/") { it.padStart(2, '0') }


            var parsedDate = LocalDate.parse(normalizedDate, formatter)

            if (recurrenceDateType == "minutes"){
                minute += recurrenceEveryRecurrence.toInt()
            }
            if (recurrenceDateType == "heures"){
                hour += recurrenceEveryRecurrence.toInt()
            }
            if (recurrenceDateType == "jours"){
                val newDate = parsedDate.plusDays(recurrenceEveryRecurrence.toLong())
                date = newDate.format(formatter)
            }
            if (recurrenceDateType == "semaines"){
                val newDate = parsedDate.plusDays(recurrenceEveryRecurrence.toLong()*7)
                date = newDate.format(formatter)
            }
            if (recurrenceDateType == "mois"){
                val newDate = parsedDate.plusMonths(recurrenceEveryRecurrence.toLong())
                date = newDate.format(formatter)
            }
            if (recurrenceDateType == "années"){
                val newDate = parsedDate.plusYears(recurrenceEveryRecurrence.toLong())
                date = newDate.format(formatter)
            }

            if (recurrenceSelectedRadio == "le JJ-MM-AAAA" && recurrenceEndDate != null){
                val endDate = LocalDate.parse(recurrenceEndDate, formatter)
                val newDateParsed = LocalDate.parse(date, formatter)
                if (endDate.isAfter(newDateParsed)){
                    scheduleNotification(context, hour, minute, date, rappelName, mapPathologies, mapMedicines, recurrenceActive, recurrenceDateType, recurrenceEveryRecurrence, recurrenceSelectedRadio, recurrenceEndDate, recurrenceEndTimes, rappelId)
                }
            }
            if (recurrenceSelectedRadio == "jamais"){
                scheduleNotification(context, hour, minute, date, rappelName, mapPathologies, mapMedicines, recurrenceActive, recurrenceDateType, recurrenceEveryRecurrence, recurrenceSelectedRadio, recurrenceEndDate, recurrenceEndTimes, rappelId)
            }
            if (recurrenceSelectedRadio == "après X fois" && recurrenceEndTimes != null){
                var endTimes = recurrenceEndTimes.toInt()
                if (endTimes > 0){
                    endTimes -= 1
                    scheduleNotification(context, hour, minute, date, rappelName, mapPathologies, mapMedicines, recurrenceActive, recurrenceDateType, recurrenceEveryRecurrence, recurrenceSelectedRadio, recurrenceEndDate, endTimes.toString(), rappelId)
                }
            }
        }
    }

    private fun showNotification(context: Context, reminderName: String, requestCode: Int, medicines:String, pathologies: String){

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "default_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Rappel Médical: $reminderName")
            .setContentText("Médicaments : $medicines \nMaladies : $pathologies")
            .setSmallIcon(R.drawable.medok)
            .setAutoCancel(true)
            .build()


        notificationManager.notify(requestCode, notification)
    }
}
