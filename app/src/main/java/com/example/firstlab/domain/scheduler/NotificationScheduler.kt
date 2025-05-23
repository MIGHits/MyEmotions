package com.example.firstlab.domain.scheduler

import com.example.firstlab.domain.entity.NotificationEntity

interface NotificationScheduler {
    fun schedule(notification: NotificationEntity)
    fun cancel(notification: NotificationEntity)
}