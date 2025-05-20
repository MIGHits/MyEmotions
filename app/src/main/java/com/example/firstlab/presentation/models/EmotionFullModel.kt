package com.example.firstlab.presentation.models

import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.presentation.mapper.parseTimeToMillis

data class EmotionFullModel(
    val id: Int? = null,
    val userId: String? = null,
    val name: String = "",
    val type: EmotionType? = null,
    val createTime: String? = null,
    val iconRes: Int? = null,
    val actions: List<String> = emptyList(),
    val company: List<String> = emptyList(),
    val location: List<String> = emptyList()
)
