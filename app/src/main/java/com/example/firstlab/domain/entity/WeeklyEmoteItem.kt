package com.example.firstlab.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeeklyEmoteItem(
    val date: String,
    val weekDay: String,
    val emotes: List<Emotion>
) : Parcelable