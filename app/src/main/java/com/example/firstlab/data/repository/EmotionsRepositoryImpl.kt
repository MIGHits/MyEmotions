package com.example.firstlab.data.repository

import com.example.firstlab.data.dao.EmotionDao
import com.example.firstlab.data.mapper.EmotionMapper
import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.repository.EmotionsRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

class EmotionsRepositoryImpl(
    private val emotionDao: EmotionDao,
    private val mapper: EmotionMapper,
    private val auth: FirebaseAuth
) : EmotionsRepository {
    override suspend fun addEmotion(emotion: EmotionEntity) {
        emotionDao.addEmotion(mapper.mapToDbModel(emotion))
    }

    override suspend fun getEmotionById(id: Int): EmotionEntity? {
        return emotionDao.getEmotionById(id)?.let { mapper.mapToDomain(it) }
    }

    override fun getEmotionsByPeriod(start: Long, end: Long): Flow<List<EmotionEntity>> {
        return mapper.mapFlow(
            emotionDao.getEmotionsByPeriod(
                auth.currentUser?.uid ?: "",
                start,
                end
            )
        )
    }

    override fun getAllEmotions(): Flow<List<EmotionEntity>> {
        return mapper.mapFlow(
            emotionDao.getAllEmotions(
                userId = auth.currentUser?.uid ?: ""
            )
        )
    }
}