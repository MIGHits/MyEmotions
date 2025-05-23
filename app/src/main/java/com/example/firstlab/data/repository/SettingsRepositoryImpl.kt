package com.example.firstlab.data.repository

import com.example.firstlab.data.dao.NotificationDao
import com.example.firstlab.data.dao.UserDao
import com.example.firstlab.data.mapper.ProfileMapper
import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.domain.entity.UserEntity
import com.example.firstlab.domain.repository.SettingsRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class SettingsRepositoryImpl(
    private val userDao: UserDao,
    private val notificationDao: NotificationDao,
    private val auth: FirebaseAuth,
    private val profileMapper: ProfileMapper
) : SettingsRepository {
    override fun getSettingsData(): Flow<Pair<UserEntity, List<NotificationEntity>>> {
        return profileMapper.mapToDomain(
            userDao.getUserWithNotifications(
                auth.currentUser?.uid ?: ""
            ) ?: emptyFlow()
        )
    }

    override suspend fun createNotification(notification: NotificationEntity) {
        notificationDao.addNotification(
            profileMapper.mapNotification(
                notification,
                auth.currentUser?.uid ?: ""
            )
        )
    }

    override suspend fun deleteNotification(notification: NotificationEntity) {
        notificationDao.deleteNotification(
            profileMapper.mapNotification(
                notification,
                auth.currentUser?.uid ?: ""
            )
        )
    }

    override suspend fun updateUser(userEntity: UserEntity) {
        userDao.updateUser(profileMapper.mapUser(userEntity))
    }
}