package com.example.firstlab.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstlab.common.Constant.TOTAL_GOALS
import com.example.firstlab.domain.entity.EmotionEntity
import com.example.firstlab.domain.usecase.CalculateSeriesUseCase
import com.example.firstlab.presentation.models.JournalModel
import com.example.firstlab.domain.usecase.GetJournalDataUseCase
import com.example.firstlab.presentation.mapper.EmotionsMapper
import com.example.firstlab.extension.isToday
import com.example.firstlab.presentation.state.JournalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.ChronoUnit


class JournalViewModel(
    private val getJournalDataUseCase: GetJournalDataUseCase,
    private val emotionsMapper: EmotionsMapper,
    private val calculateSeriesUseCase: CalculateSeriesUseCase
) : ViewModel() {
    private val _journalState = MutableStateFlow<JournalState>(JournalState.Empty)
    val journalState: StateFlow<JournalState> get() = _journalState

    init {
        getJournalData()
    }

    fun getJournalData() {
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
                                series = calculateSeriesUseCase(emotions),
                                today = emotions.filter { it.createTime?.isToday() ?: false }
                                    .mapNotNull { it.type }.take(TOTAL_GOALS)
                            )
                        )
                    }
                }
            }
        }
    }
}