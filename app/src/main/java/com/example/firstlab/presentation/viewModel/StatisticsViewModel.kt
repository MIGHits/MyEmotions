package com.example.firstlab.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstlab.common.Constant.RELEASE_YEAR
import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.entity.EmotionType
import com.example.firstlab.domain.usecase.GetStatisticsDataUseCase
import com.example.firstlab.presentation.mapper.getTimeOfDay
import com.example.firstlab.presentation.mapper.truncateToDay
import com.example.firstlab.presentation.models.EmotesCategory
import com.example.firstlab.presentation.models.Emotion
import com.example.firstlab.presentation.models.MoodCategory
import com.example.firstlab.presentation.models.StatisticsContent
import com.example.firstlab.presentation.models.TimeOfDay
import com.example.firstlab.presentation.models.WeekInfo
import com.example.firstlab.presentation.models.WeeklyEmoteItem
import com.example.firstlab.presentation.state.StatisticsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class StatisticsViewModel(private val getStatisticsDataUseCase: GetStatisticsDataUseCase) :
    ViewModel() {
    private val _statisticsStateMap = mutableMapOf<Int, MutableStateFlow<StatisticsState>>()
    private var weeks: List<WeekInfo>
    private var currentWeekIndex: Int

    init {
        val result = generateWeekList(RELEASE_YEAR, LocalDate.now().year + 1)
        weeks = result.first
        currentWeekIndex = result.second
        weeks.forEachIndexed { index, _ ->
            _statisticsStateMap.getOrPut(index) {
                MutableStateFlow(
                    StatisticsState.Empty
                )
            }
        }
        getWeekData(currentWeekIndex)
    }

    fun getWeeksInfo(): Pair<List<WeekInfo>, Int> {
        return Pair(weeks, currentWeekIndex)
    }

    fun getStatisticsStateForWeek(weekIndex: Int): StateFlow<StatisticsState> {
        return _statisticsStateMap.getOrPut(weekIndex) { MutableStateFlow(StatisticsState.Empty) }
    }

    fun getWeekData(weekIndex: Int) {
        val currentStateFlow =
            _statisticsStateMap.getOrPut(weekIndex) { MutableStateFlow(StatisticsState.Empty) }

        if (currentStateFlow.value is StatisticsState.Content) {
            return
        }

        currentStateFlow.update { StatisticsState.Loading }

        viewModelScope.launch {
            val chosenWeek = weeks[weekIndex]
            getStatisticsDataUseCase(
                start = chosenWeek.startOfWeekMs,
                end = chosenWeek.endOfWeekMs
            ).collect { emotions ->
                currentStateFlow.update {
                    StatisticsState.Content(
                        StatisticsContent(
                            emotionsCategory = divideOnCategories(emotions),
                            mostFrequentEmotions = calculateMostFrequent(emotions),
                            dailyMood = calculateMood(emotions),
                            weeklyEmotions = divideByWeekDays(emotions, chosenWeek),
                            noteCount = emotions.size
                        )
                    )
                }
            }
        }
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

    private fun generateWeekList(startYear: Int, endYear: Int): Pair<List<WeekInfo>, Int> {
        val weeks = mutableListOf<WeekInfo>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, startYear)
        calendar.set(Calendar.WEEK_OF_YEAR, 1)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)


        val dateFormat = SimpleDateFormat("d", Locale("ru"))
        val dateFormatWithMonth = SimpleDateFormat("d MMM", Locale("ru"))
        val currentTime = System.currentTimeMillis()
        var currentWeekIndex = -1

        var index = 0
        while (calendar.get(Calendar.YEAR) <= endYear) {
            val startOfWeek = calendar.time
            val startOfWeekMs = startOfWeek.time
            calendar.add(Calendar.DAY_OF_MONTH, 6)

            val endOfWeek = calendar.time
            val endOfWeekMs = endOfWeek.time

            val weekString = if (startOfWeek.month == endOfWeek.month) {
                "${dateFormat.format(startOfWeek)} – ${
                    dateFormatWithMonth.format(endOfWeek).removeSuffix(".")
                }"
            } else {
                "${dateFormatWithMonth.format(startOfWeek).removeSuffix(".")} – ${
                    dateFormatWithMonth.format(endOfWeek).removeSuffix(".")
                }"
            }

            weeks.add(WeekInfo(weekString, startOfWeekMs, endOfWeekMs))

            if (currentWeekIndex == -1 && currentTime in startOfWeekMs..endOfWeekMs) {
                currentWeekIndex = index
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            index++
        }

        return weeks to if (currentWeekIndex >= 0) currentWeekIndex else 0
    }
}