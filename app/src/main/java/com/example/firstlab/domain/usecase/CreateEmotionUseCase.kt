package com.example.firstlab.domain.usecase


import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.repository.EmotionsRepository

open class CreateEmotionUseCase(private val repository: EmotionsRepository) {
    open suspend operator fun invoke(emotion: EmotionEntity) {
        repository.addEmotion(emotion)
    }
}