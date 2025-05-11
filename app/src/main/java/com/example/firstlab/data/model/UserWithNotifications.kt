package com.example.firstlab.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithNotifications(
    @Embedded val user: UserDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val notifications: List<NotificationDbModel>
)