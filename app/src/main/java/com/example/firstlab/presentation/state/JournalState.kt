package com.example.firstlab.presentation.state

import com.example.firstlab.presentation.models.JournalModel

sealed class JournalState {
    data object Empty : JournalState()
    data object Loading : JournalState()
    data class Content(val content: JournalModel) : JournalState()
}