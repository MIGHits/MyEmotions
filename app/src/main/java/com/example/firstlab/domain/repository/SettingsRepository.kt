package com.example.firstlab.domain.repository

import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettingsData(): Flow<Pair<UserEntity, List<NotificationEntity>>>
    suspend fun createNotification(notification: NotificationEntity)
    suspend fun deleteNotification(notification: NotificationEntity)
    suspend fun updateUser(userEntity: UserEntity)
}