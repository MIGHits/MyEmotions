package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.domain.scheduler.NotificationScheduler

class ScheduleNotificationsUseCase(private val scheduler: NotificationScheduler) {
    operator fun invoke(notifications: List<NotificationEntity>) {
        notifications.forEach { scheduler.schedule(it) }
    }
}