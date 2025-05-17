package com.example.firstlab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.firstlab.models.EmotionType

@Entity(tableName = "Emotions")
data class EmotionDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val type: EmotionType,
    val userId: String,
    val createTime: Long,
    val iconRes: Int,
    val actions: List<String>,
    val company: List<String>,
    val location: List<String>
)