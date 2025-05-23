package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.domain.scheduler.NotificationScheduler

class CancelNotificationsUseCase(private val scheduler: NotificationScheduler) {
    operator fun invoke(notifications: List<NotificationEntity>) {
        notifications.forEach { scheduler.cancel(it) }
    }

    operator fun invoke(notification: NotificationEntity) {
        scheduler.cancel(notification)
    }
}