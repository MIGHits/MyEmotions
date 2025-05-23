package com.example.firstlab.presentation.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstlab.domain.entity.UserEntity
import com.example.firstlab.domain.usecase.CancelNotificationsUseCase
import com.example.firstlab.domain.usecase.CreateNotificationUseCase
import com.example.firstlab.domain.usecase.GetProfileDataUseCase
import com.example.firstlab.domain.usecase.LogoutUseCase
import com.example.firstlab.domain.usecase.RemoveNotificationUseCase
import com.example.firstlab.domain.usecase.ScheduleNotificationsUseCase
import com.example.firstlab.domain.usecase.UpdateUserUseCase
import com.example.firstlab.presentation.mapper.NotificationMapper
import com.example.firstlab.presentation.models.NotificationPresentationModel
import com.example.firstlab.presentation.models.ProfileSettings
import com.example.firstlab.presentation.state.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getProfileDataUseCase: GetProfileDataUseCase,
    private val createNotificationUseCase: CreateNotificationUseCase,
    private val deleteNotificationUseCase: RemoveNotificationUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val notificationMapper: NotificationMapper,
    private val scheduleNotificationsUseCase: ScheduleNotificationsUseCase,
    private val cancelNotificationsUseCase: CancelNotificationsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _settingsState = MutableStateFlow<SettingsState>(SettingsState.Initial)
    val settingsState: StateFlow<SettingsState> get() = _settingsState

    init {
        getProfileData()
    }

    private fun getProfileData() {
        viewModelScope.launch {
            _settingsState.update { SettingsState.Loading }
            getProfileDataUseCase().collect { data ->
                _settingsState.update {
                    SettingsState.Content(
                        ProfileSettings(
                            user = data.first,
                            notifications = notificationMapper.mapToPresentation(data.second)
                        )
                    )
                }
                if (data.first.isNotificationEnabled && data.second.isNotEmpty()) {
                    scheduleNotificationsUseCase(data.second)
                } else if (data.second.isNotEmpty()) {
                    cancelNotificationsUseCase(data.second)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun createNotification(notification: NotificationPresentationModel) {
        viewModelScope.launch {
            createNotificationUseCase(notificationMapper.mapToDomain(notification))
        }

    }

    fun removeNotification(notification: NotificationPresentationModel) {
        viewModelScope.launch {
            cancelNotificationsUseCase(notificationMapper.mapToDomain(notification))
            deleteNotificationUseCase(notificationMapper.mapToDomain(notification))
        }
    }

    fun toggleSwitch(user: UserEntity) {
        viewModelScope.launch {
            updateUserUseCase(user)
        }
    }

    @SuppressLint("DefaultLocale")
    fun setTime(state: ProfileSettings, hours: Int, minutes: Int) {
        val formattedHours = hours.toString()
        val formattedMinutes = String.format("%02d", minutes)
        _settingsState.update {
            SettingsState.Content(
                state.copy(
                    selectedTime = "$formattedHours:$formattedMinutes",
                )
            )
        }
    }
}