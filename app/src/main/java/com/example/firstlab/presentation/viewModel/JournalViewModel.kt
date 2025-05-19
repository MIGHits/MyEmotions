package com.example.firstlab.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.presentation.models.JournalModel
import com.example.firstlab.domain.usecase.GetJournalDataUseCase
import com.example.firstlab.presentation.mapper.EmotionsMapper
import com.example.firstlab.presentation.mapper.isToday
import com.example.firstlab.presentation.state.JournalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.DayOfWeek
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.TemporalAdjusters


class JournalViewModel(
    private val getJournalDataUseCase: GetJournalDataUseCase,
    private val emotionsMapper: EmotionsMapper
) : ViewModel() {
    private val _journalState = MutableStateFlow<JournalState>(JournalState.Empty)
    val journalState: StateFlow<JournalState> get() = _journalState

    init {
        getJournalData()
    }

    private fun getJournalData() {
        viewModelScope.launch {
            _journalState.update { JournalState.Loading }
            getJournalDataUseCase().collect { emotions ->
                _journalState.update {
                    if (emotions.isEmpty()) {
                        JournalState.Empty
                    } else {
                        JournalState.Content(
                            JournalModel(
                                emotions = emotionsMapper.mapToJournalItem(emotions),
                                amountOFEmotions = emotions.size,
                                series = calculateSeries(emotions),
                                today = emotions.filter { it.createTime?.isToday() ?: false }
                                    .mapNotNull { it.type }
                            )
                        )
                    }
                }
            }
        }
    }


    private fun calculateSeries(emotions: List<EmotionEntity>): Int {
        if (emotions.isEmpty()) return 0

        val uniqueDates = emotions
            .mapNotNull { emotion ->
                emotion.createTime?.let { time ->
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
                        .toLocalDate()
                }
            }
            .distinct()

        if (uniqueDates.isEmpty()) return 0

        var maxStreak = 1
        var currentStreak = 1

        for (i in 1 until uniqueDates.size) {
            val daysBetween = ChronoUnit.DAYS.between(uniqueDates[i], uniqueDates[i - 1])
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

    private fun getPeriod(): Pair<Long, Long> {
        val today = LocalDate.now()
        val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endOfWeek = startOfWeek.plusDays(6)
        return Pair(
            first = startOfWeek.atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
            second = endOfWeek.atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        )
    }
}