package com.example.firstlab.presentation.models

import android.os.Parcelable
import com.example.firstlab.domain.entity.EmotionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Emotion(
    val icon: Int,
    val name: String,
    val frequent: Int = 0,
    val type: EmotionType? = null
) : Parcelable
