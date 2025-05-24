package com.example.firstlab

import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.domain.entity.TimeOfDay
import com.example.firstlab.domain.entity.WeekInfo
import com.example.firstlab.domain.usecase.CalculateStatisticsContentUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class CalculateStatisticsTest {

    private lateinit var useCase: CalculateStatisticsContentUseCase

    @Before
    fun setup() {
        useCase = CalculateStatisticsContentUseCase()
    }

    private fun nowMs(): Long = System.currentTimeMillis()

    private fun createEmotion(
        name: String,
        type: EmotionType,
        timestamp: Long,
        icon: Int = 1
    ): EmotionEntity {
        return EmotionEntity(
            id = Random().nextInt(),
            name = name,
            type = type,
            createTime = timestamp,
            iconRes = icon
        )
    }

    private fun createWeekInfo(): WeekInfo {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val start = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val end = calendar.timeInMillis
        return WeekInfo("Week", start, end)
    }

    @Test
    fun `invoking with mixed emotions returns correct category distribution`() = runTest {
        val now = nowMs()
        val emotions = listOf(
            createEmotion("Joy", EmotionType.YELLOW, now),
            createEmotion("Calm", EmotionType.GREEN, now),
            createEmotion("Anger", EmotionType.RED, now),
            createEmotion("Sad", EmotionType.BLUE, now),
            createEmotion("Joy", EmotionType.YELLOW, now),
        )

        val result = useCase(emotions, createWeekInfo())

        assertEquals(5, result.noteCount)

        val yellow = result.emotionsCategory.find { it.category.second == EmotionType.YELLOW }
        assertEquals(40f, yellow?.category?.first)

        val mostFrequent = result.mostFrequentEmotions.firstOrNull { it.name == "Joy" }
        assertEquals(2, mostFrequent?.frequent)
    }

    @Test
    fun `weeklyEmotions divides emotions correctly by weekday`() = runTest {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val monday = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_WEEK, 1)
        val tuesday = calendar.timeInMillis

        val emotions = listOf(
            createEmotion("Joy", EmotionType.YELLOW, monday),
            createEmotion("Anger", EmotionType.RED, tuesday)
        )

        val result = useCase(emotions, createWeekInfo())

        assertEquals(7, result.weeklyEmotions.size)

        val mondayItem = result.weeklyEmotions[0]
        assertEquals("Joy", mondayItem.emotes.firstOrNull()?.name)

        val tuesdayItem = result.weeklyEmotions[1]
        assertEquals("Anger", tuesdayItem.emotes.firstOrNull()?.name)
    }

    @Test
    fun `calculateMood correctly groups emotions by time of day`() = runTest {
        val morningTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
        }.timeInMillis

        val nightTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
        }.timeInMillis

        val emotions = listOf(
            createEmotion("Joy", EmotionType.YELLOW, morningTime),
            createEmotion("Anger", EmotionType.RED, nightTime),
            createEmotion("Anger", EmotionType.RED, nightTime)
        )

        val result = useCase(emotions, createWeekInfo())

        val morningMood = result.dailyMood.find { it.timeOfDay == TimeOfDay.MORNING }
        assertNotNull(morningMood)
        assertEquals(1f, morningMood!!.category.first)

        val nightMood = result.dailyMood.find { it.timeOfDay == TimeOfDay.LATE_EVENING }
        assertNotNull(nightMood)
        assertEquals(1f, nightMood!!.category.first, 0.001f)
    }

    @Test
    fun `calculateMostFrequent aggregates correctly`() = runTest {
        val now = nowMs()
        val emotions = listOf(
            createEmotion("Sad", EmotionType.BLUE, now),
            createEmotion("Sad", EmotionType.BLUE, now),
            createEmotion("Sad", EmotionType.BLUE, now),
            createEmotion("Joy", EmotionType.YELLOW, now)
        )

        val result = useCase(emotions, createWeekInfo())

        val sad = result.mostFrequentEmotions.firstOrNull { it.name == "Sad" }
        val joy = result.mostFrequentEmotions.firstOrNull { it.name == "Joy" }

        assertEquals(3, sad?.frequent)
        assertEquals(1, joy?.frequent)
    }
}
