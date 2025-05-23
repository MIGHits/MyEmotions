package com.example.firstlab.domain.entity

data class UserEntity(
    val id: String,
    val username: String,
    val avatar: String,
    val isNotificationEnabled: Boolean,
    val isFingerprintEnabled: Boolean
)
