package com.medok.app.receivers

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.medok.app.R

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            try {
                reconfigureAlarms(context)
            } catch (e: Exception) {
                notifyError(context, "Impossible de replanifier les alarmes. Erreur : ${e.message}")
            }
        }
    }

    private fun notifyError(context: Context, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "error_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Erreur de Rappel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Erreur lors du redémarrage")
            .setContentText(message)
            .setSmallIcon(R.drawable.medok)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
    }


    @SuppressLint("ScheduleExactAlarm")
    private fun reconfigureAlarms(context: Context) {
        val sharedPreferences = context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)
        val alarmData = sharedPreferences.getStringSet("ALARMS", emptySet()) ?: return

        for (alarm in alarmData) {
            val parts = alarm.split("|")
            if (parts.size == 3) {
                val name = parts[0]
                val timeInMillis = parts[1].toLongOrNull() ?: continue
                val id = parts[2].toIntOrNull() ?: continue

                if (timeInMillis > System.currentTimeMillis()) {
                    val intent = Intent(context, NotificationReceiver::class.java).apply {
                        putExtra("RAPPEL_NAME", name)
                        putExtra("REQUEST_CODE", id)
                    }

                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        id,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )

                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
                }
            }
        }
    }
}
