package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.repository.EmotionsRepository

open class GetEmotionByIdUseCase(private val repository: EmotionsRepository) {
    open suspend operator fun invoke(id: Int): EmotionEntity {
        return repository.getEmotionById(id) ?: EmotionEntity()
    }
}