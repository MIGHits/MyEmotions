package com.example.firstlab.presentation.models

import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.entity.EmotionType

data class JournalModel(
    val emotions: List<JournalItem> = listOf(),
    val amountOFEmotions: Int = 0,
    val series: Int = 0,
    val today: List<EmotionType> = listOf()
)
