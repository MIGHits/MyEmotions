package com.example.firstlab

import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.domain.usecase.CalculateSeriesUseCase
import com.example.firstlab.extension.isToday
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId


class JournalTest {

    private val useCase = CalculateSeriesUseCase()

    @Test
    fun `given empty emotions list, returns 0 streak`() {
        val emotions = emptyList<EmotionEntity>()
        val result = useCase(emotions)
        assertEquals(0, result)
    }

    @Test
    fun `given single day with multiple emotions, returns 1 streak`() {
        val today = LocalDate.now()
        val emotions = listOf(
            EmotionEntity(
                type = EmotionType.YELLOW,
                createTime = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            ),
            EmotionEntity(
                type = EmotionType.BLUE,
                createTime = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            )
        )
        val result = useCase(emotions)
        assertEquals(1, result)
    }

    @Test
    fun `given consecutive days with multiple emotions, returns correct streak`() {
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        val emotions = listOf(
            EmotionEntity(
                type = EmotionType.YELLOW,
                createTime = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            ),
            EmotionEntity(
                type = EmotionType.BLUE,
                createTime = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            ),
            EmotionEntity(
                type = EmotionType.YELLOW,
                createTime = yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant()
                    .toEpochMilli()
            ),
            EmotionEntity(
                type = EmotionType.BLUE,
                createTime = yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant()
                    .toEpochMilli()
            )
        )
        val result = useCase(emotions)
        assertEquals(2, result)
    }

    @Test
    fun `given non-consecutive days with multiple emotions, returns 1 streak`() {
        val today = LocalDate.now()
        val threeDaysAgo = today.minusDays(3)
        val emotions = listOf(
            EmotionEntity(
                type = EmotionType.YELLOW,
                createTime = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            ),
            EmotionEntity(
                type = EmotionType.BLUE,
                createTime = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            ),
            EmotionEntity(
                type = EmotionType.YELLOW,
                createTime = threeDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant()
                    .toEpochMilli()
            ),
            EmotionEntity(
                type = EmotionType.BLUE,
                createTime = threeDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant()
                    .toEpochMilli()
            )
        )
        val result = useCase(emotions)
        assertEquals(1, result)
    }

    @Test
    fun `given days with single emotion, ignores them and returns 0 streak`() {
        val today = LocalDate.now()
        val emotions = listOf(
            EmotionEntity(
                type = EmotionType.YELLOW,
                createTime = today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            )
        )
        val result = useCase(emotions)
        assertEquals(0, result)
    }

    @Test
    fun `filter emotions for today returns only today's emotions`() {
        val today = System.currentTimeMillis()
        val yesterday = today - 24 * 60 * 60 * 1000L
        val emotions = listOf(
            EmotionEntity(createTime = today, name = "happy", type = EmotionType.GREEN, iconRes = 0),
            EmotionEntity(createTime = yesterday, name = "sad", type = EmotionType.RED, iconRes = 0)
        )
        val todayEmotions = emotions.filter { it.createTime?.isToday() ?: false }
        assertEquals(1, todayEmotions.size)
        assertEquals("happy", todayEmotions.first().name)
    }
}