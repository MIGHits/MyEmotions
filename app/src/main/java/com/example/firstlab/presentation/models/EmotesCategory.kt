package com.example.firstlab.presentation.models

import android.os.Parcelable
import com.example.firstlab.domain.entity.EmotionType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmotesCategory(
    val category: Pair<Float, EmotionType>,
    val timeOfDay: TimeOfDay? = null
) : Parcelable
