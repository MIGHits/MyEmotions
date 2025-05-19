package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.repository.EmotionsRepository
import kotlinx.coroutines.flow.Flow

class GetJournalDataUseCase(private val repository: EmotionsRepository) {
    operator fun invoke(): Flow<List<EmotionEntity>> {
        return repository.getAllEmotions();
    }
}