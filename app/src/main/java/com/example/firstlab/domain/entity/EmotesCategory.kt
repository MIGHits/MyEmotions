package com.example.firstlab.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmotesCategory(
    val category: Pair<Float, EmotionType>
) : Parcelable
