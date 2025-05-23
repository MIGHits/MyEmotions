package com.example.firstlab.domain.entity

import java.time.LocalTime

data class NotificationEntity(
    val id: Int? = null,
    val time: LocalTime
)