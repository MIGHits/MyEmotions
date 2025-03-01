package com.example.firstlab.models

data class Emotion(
    val icon: Int,
    val name: String,
    val frequent: Int = 0,
    val type: EmotionType? = null
)
