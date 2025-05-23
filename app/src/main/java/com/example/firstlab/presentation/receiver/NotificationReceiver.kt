package com.example.firstlab.presentation.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.firstlab.R
import com.example.firstlab.common.Constant.NOTIFICATION_ID
import com.example.firstlab.common.Constant.NOTIFICATION_TEXT
import com.example.firstlab.common.Constant.USER_ID
import com.google.firebase.auth.FirebaseAuth

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val text = intent.getStringExtra(NOTIFICATION_TEXT) ?: return
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        val userId = intent.getStringExtra(USER_ID) ?: return

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = context.getString(R.string.channel_id)

        val channel = NotificationChannel(
            channelId,
            context.getString(R.string.notification_channel),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        if (userId == FirebaseAuth.getInstance().currentUser?.uid) {
            val notification = NotificationCompat.Builder(context, channelId)
                .setContentTitle(context.getString(R.string.notification_tittle))
                .setContentText(text)
                .setSmallIcon(R.drawable.notification_icon)
                .build()

            notificationManager.notify(notificationId, notification)
        }
    }
}