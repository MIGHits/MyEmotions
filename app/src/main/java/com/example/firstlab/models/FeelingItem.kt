package com.example.firstlab.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeelingItem(
    val time: String,
    val name: String,
    val icon: Int,
    val background: Int,
    val color: Int
) : Parcelable
