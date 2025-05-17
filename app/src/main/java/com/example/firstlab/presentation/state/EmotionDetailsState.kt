package com.example.firstlab.presentation.state

import com.example.firstlab.models.EmotionType

data class EmotionDetailsState(
    val emotionType: EmotionType? = null,
    val name: String = "",
    val createTime: Long? = null,
    val iconRes: Int? = null,
    val actions: List<String> = emptyList(),
    val company: List<String> = emptyList(),
    val location: List<String> = emptyList()
)