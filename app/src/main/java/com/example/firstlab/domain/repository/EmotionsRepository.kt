package com.example.firstlab.domain.repository

import com.example.firstlab.domain.entity.EmotionEntity
import kotlinx.coroutines.flow.Flow

interface EmotionsRepository {
    suspend fun addEmotion(emotion: EmotionEntity)
    suspend fun getEmotionById(id: Int): EmotionEntity?
    fun getEmotionsByPeriod(start: Long, end: Long): Flow<List<EmotionEntity>>
}