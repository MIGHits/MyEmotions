package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.domain.repository.SettingsRepository

class CreateNotificationUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(notification: NotificationEntity) {
        repository.createNotification(notification)
    }
}