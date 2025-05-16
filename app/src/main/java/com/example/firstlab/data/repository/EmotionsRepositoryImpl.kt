package com.example.firstlab.data.repository

import com.example.firstlab.data.dao.EmotionDao
import com.example.firstlab.domain.repository.EmotionsRepository

class EmotionsRepositoryImpl(private val emotionDao: EmotionDao) : EmotionsRepository {
}