package com.example.firstlab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDbModel(
    @PrimaryKey val id: String,
    val username: String,
    val avatar: String,
    val isNotificationEnabled: Boolean = false,
    val isFingerprintEnabled: Boolean = false
)