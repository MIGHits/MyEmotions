package com.example.firstlab.domain.entity

data class EmotionEntity(
    val id: Int? = null,
    val userId: String? = null,
    val name: String = "",
    val type: EmotionType? = null,
    val createTime: Long? = null,
    val iconRes: Int? = null,
    val actions: List<String> = emptyList(),
    val company: List<String> = emptyList(),
    val location: List<String> = emptyList()
)
