package com.example.firstlab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "notifications")
data class NotificationDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userId: Int,
    val time: LocalTime
)