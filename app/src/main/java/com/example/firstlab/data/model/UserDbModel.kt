package com.example.firstlab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val username: String,
    val isNotificationEnabled: Boolean = false,
    val isFingerprintEnabled: Boolean = false
)