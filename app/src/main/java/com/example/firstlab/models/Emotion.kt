package com.example.firstlab.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Emotion(
    val icon: Int,
    val name: String,
    val frequent: Int = 0,
    val type: EmotionType? = null
) : Parcelable
