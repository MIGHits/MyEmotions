package com.example.firstlab.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstlab.common.Constant.RELEASE_YEAR
import com.example.firstlab.domain.usecase.GetStatisticsDataUseCase
import com.example.firstlab.domain.entity.StatisticsContent
import com.example.firstlab.domain.entity.WeekInfo
import com.example.firstlab.domain.usecase.CalculateStatisticsContentUseCase
import com.example.firstlab.presentation.state.StatisticsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class StatisticsViewModel(
    private val getStatisticsDataUseCase: GetStatisticsDataUseCase,
    private val calculateStatisticsContentUseCase: CalculateStatisticsContentUseCase
) :
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
                        calculateStatisticsContentUseCase(emotions, chosenWeek)
                    )
                }
            }
        }
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