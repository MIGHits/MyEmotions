package com.example.firstlab.presentation.state

import com.example.firstlab.presentation.models.ProfileSettings

sealed class SettingsState {
    data object Initial : SettingsState()
    data object Loading : SettingsState()
    data class Content(val content: ProfileSettings) : SettingsState()
}