package com.example.firstlab.presentation.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.firstlab.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val text = intent.getStringExtra("notification_text") ?: return
        val notificationId = intent.getIntExtra("notification_id", 0)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "daily_notifications"

        val channel = NotificationChannel(
            channelId,
            "Ежедневные уведомления",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Напоминание")
            .setContentText(text)
            .setSmallIcon(R.drawable.notification_icon)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}