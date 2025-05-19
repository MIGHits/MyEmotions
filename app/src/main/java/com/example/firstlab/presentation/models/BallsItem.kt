package com.example.firstlab.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BallsItem(
    val color: Int,
    val name: String,
    var isSelected: Boolean = false,
    val description: String = ""
) : Parcelable