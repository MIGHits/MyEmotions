package com.example.firstlab.presentation.models

data class StatisticsContent(
    val emotionsCategory: List<EmotesCategory>,
    val weeklyEmotions: List<WeeklyEmoteItem>,
    val mostFrequentEmotions: List<Emotion>,
    val dailyMood: List<MoodCategory>,
    val noteCount: Int
)