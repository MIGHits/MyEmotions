package com.example.firstlab.presentation.models

import android.annotation.SuppressLint
import com.example.firstlab.domain.entity.UserEntity
import java.util.Calendar

@SuppressLint("DefaultLocale")
data class ProfileSettings(
    val user: UserEntity,
    val notifications: List<NotificationPresentationModel>,
    val isProfileToolBarExpanded: Boolean = true,
    val selectedTime: String = "${
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }:${String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE))}"
)