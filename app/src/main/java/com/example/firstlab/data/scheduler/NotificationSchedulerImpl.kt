package com.example.firstlab.data.scheduler

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.domain.scheduler.NotificationScheduler
import com.example.firstlab.presentation.receiver.NotificationReceiver
import java.util.Calendar

class NotificationSchedulerImpl(private val context: Context) : NotificationScheduler {
    @SuppressLint("ScheduleExactAlarm")
    override fun schedule(notification: NotificationEntity) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("notification_text", "Пора оставить новую запись")
            putExtra("notification_id", notification.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notification.id ?: 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val now = Calendar.getInstance()
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, notification.time.hour)
            set(Calendar.MINUTE, notification.time.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(now)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    override fun cancel(notification: NotificationEntity) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notification.id ?: 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}