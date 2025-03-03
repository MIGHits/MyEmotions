package com.example.firstlab.models

data class WeeklyEmoteItem(
    val date: String,
    val weekDay: String,
    val emotes: List<Emotion>
)