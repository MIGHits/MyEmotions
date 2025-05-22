package com.example.firstlab.presentation.state

import com.example.firstlab.presentation.models.StatisticsContent

sealed class StatisticsState {
    data object Empty : StatisticsState()
    data object Loading : StatisticsState()
    data class Content(val content: StatisticsContent) : StatisticsState()
}