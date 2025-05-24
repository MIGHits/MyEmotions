package com.example.firstlab.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoodCategory(
    val category: Pair<Float, EmotionType>,
    val timeOfDay: TimeOfDay
) : Parcelable
