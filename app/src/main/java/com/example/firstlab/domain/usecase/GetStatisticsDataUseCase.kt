package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.repository.EmotionsRepository
import kotlinx.coroutines.flow.Flow

class GetStatisticsDataUseCase(private val repository: EmotionsRepository) {
    operator fun invoke(start: Long, end: Long): Flow<List<EmotionEntity>> {
        return repository.getEmotionsByPeriod(start, end)
    }
}