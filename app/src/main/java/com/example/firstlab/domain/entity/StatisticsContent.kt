package com.example.firstlab.domain.entity

data class StatisticsContent(
    val emotionsCategory: List<EmotesCategory>,
    val weeklyEmotions: List<WeeklyEmoteItem>,
    val mostFrequentEmotions: List<Emotion>,
    val dailyMood: List<MoodCategory>,
    val noteCount: Int
)