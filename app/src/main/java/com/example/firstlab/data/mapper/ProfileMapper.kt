package com.example.firstlab.data.mapper

import com.example.firstlab.data.model.NotificationDbModel
import com.example.firstlab.data.model.UserDbModel
import com.example.firstlab.data.model.UserWithNotifications
import com.example.firstlab.domain.entity.NotificationEntity
import com.example.firstlab.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileMapper {
    private fun mapNotification(notification: NotificationDbModel): NotificationEntity {
        return NotificationEntity(id = notification.id ?: 0, time = notification.time)
    }

    fun mapNotification(
        notificationEntity: NotificationEntity,
        userId: String
    ): NotificationDbModel {
        return NotificationDbModel(
            id = notificationEntity.id,
            userId = userId,
            time = notificationEntity.time
        )
    }


    private fun mapToDomain(dbModel: UserWithNotifications): Pair<UserEntity, List<NotificationEntity>> {

        dbModel.apply {
            return Pair(
                UserEntity(
                    id = user.id,
                    username = user.username,
                    avatar = user.avatar,
                    isNotificationEnabled = user.isNotificationEnabled,
                    isFingerprintEnabled = user.isFingerprintEnabled
                ),
                (notifications.map { mapNotification(it) })
            )
        }
    }

    fun mapToDomain(dbFlow: Flow<UserWithNotifications>): Flow<Pair<UserEntity, List<NotificationEntity>>> {
        return dbFlow.map { mapToDomain(it) }
    }

    fun mapUser(user: UserEntity): UserDbModel {
        return UserDbModel(
            id = user.id,
            username = user.username,
            avatar = user.avatar,
            isNotificationEnabled = user.isNotificationEnabled,
            isFingerprintEnabled = user.isFingerprintEnabled
        )
    }
}