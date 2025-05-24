package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.entity.EmotionEntity
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.ChronoUnit

class CalculateSeriesUseCase {
    operator fun invoke(emotions: List<EmotionEntity>): Int {
        if (emotions.isEmpty()) return 0
        val dateToCount = emotions
            .mapNotNull { emotion ->
                emotion.createTime?.let { time ->
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
                        .toLocalDate()
                }
            }
            .groupingBy { it }
            .eachCount()
            .filterValues { it >= 2 }
            .keys
            .sortedDescending()

        if (dateToCount.isEmpty()) return 0

        var maxStreak = 1
        var currentStreak = 1

        for (i in 1 until dateToCount.size) {
            val daysBetween = ChronoUnit.DAYS.between(dateToCount[i], dateToCount[i - 1])
            when (daysBetween) {
                1L -> {
                    currentStreak++
                    maxStreak = maxOf(maxStreak, currentStreak)
                }

                0L -> continue
                else -> currentStreak = 1
            }
        }
        return maxStreak
    }
}
