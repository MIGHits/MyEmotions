package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.EmotesCategory
import com.example.firstlab.domain.entity.Emotion
import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.domain.entity.MoodCategory
import com.example.firstlab.domain.entity.StatisticsContent
import com.example.firstlab.domain.entity.WeekInfo
import com.example.firstlab.domain.entity.WeeklyEmoteItem
import com.example.firstlab.extension.getTimeOfDay
import com.example.firstlab.extension.truncateToDay
import com.example.firstlab.domain.entity.TimeOfDay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalculateStatisticsContentUseCase {
    operator fun invoke(
        emotions: List<EmotionEntity>,
        weekInfo: WeekInfo
    ): StatisticsContent {
        return StatisticsContent(
            emotionsCategory = divideOnCategories(emotions),
            mostFrequentEmotions = calculateMostFrequent(emotions),
            weeklyEmotions = divideByWeekDays(emotions, weekInfo),
            noteCount = emotions.size,
            dailyMood = calculateMood(emotions)
        )
    }

    private fun calculateMostFrequent(emotions: List<EmotionEntity>): List<Emotion> {
        val frequencies: MutableList<Emotion> = mutableListOf()
        emotions.groupBy { it.name }.forEach { item ->
            frequencies.add(
                Emotion(
                    icon = item.value.first().iconRes ?: 0,
                    name = item.key,
                    frequent = item.value.size,
                    item.value.first().type
                )
            )
        }
        return frequencies
    }

    private fun divideByWeekDays(
        emotions: List<EmotionEntity>,
        weekInfo: WeekInfo
    ): List<WeeklyEmoteItem> {
        val dateFormat = SimpleDateFormat("d MMM", Locale("ru"))
        val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale("ru"))

        val emotionsByDate = emotions.groupBy { Date(it.createTime ?: 0).truncateToDay() }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = weekInfo.startOfWeekMs

        val fullWeek = List(7) {
            val date = calendar.time
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            date
        }

        return fullWeek.map { date ->
            val day = date.truncateToDay()
            val dateStr = dateFormat.format(day)
            val weekDay = dayOfWeekFormat.format(day).replaceFirstChar { it.uppercaseChar() }

            val emotionsInDay = emotionsByDate[day] ?: emptyList()

            val groupedEmotions = emotionsInDay
                .groupBy { it.name to it.type }
                .map { (key, group) ->
                    val (name, type) = key
                    Emotion(
                        icon = group.first().iconRes ?: 0,
                        name = name,
                        frequent = group.size,
                        type = type
                    )
                }

            WeeklyEmoteItem(
                date = dateStr,
                weekDay = weekDay,
                emotes = groupedEmotions
            )
        }
    }


    private fun calculateMood(emotions: List<EmotionEntity>): List<MoodCategory> {
        val emotionsByTime = emotions.groupBy { it.createTime?.getTimeOfDay() }
        val mood = mutableListOf<MoodCategory>()
        for ((timeOfDay, emotionListInTime) in emotionsByTime) {
            val total = emotionListInTime.size.toFloat()
            val countByType =
                emotionListInTime.groupingBy { it.type ?: EmotionType.RED }.eachCount()
            for ((type, count) in countByType) {
                val percentage = (count / total)
                mood.add(
                    MoodCategory(
                        category = Pair(percentage, type),
                        timeOfDay = timeOfDay ?: TimeOfDay.DAY
                    )
                )
            }
        }
        return mood
    }

    private fun divideOnCategories(emotions: List<EmotionEntity>): List<EmotesCategory> {
        val emotionsCategory: MutableList<EmotesCategory> = mutableListOf()
        EmotionType.entries.forEach { type ->
            emotionsCategory.add(
                EmotesCategory(
                    category = Pair(
                        first = (emotions.filter { it.type == type }.size.toFloat() / emotions.size) * 100,
                        second = type
                    )
                )
            )
        }
        return emotionsCategory
    }
}
