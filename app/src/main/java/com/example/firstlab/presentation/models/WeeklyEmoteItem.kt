package com.example.firstlab.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeeklyEmoteItem(
    val date: String,
    val weekDay: String,
    val emotes: List<Emotion>
) : Parcelable