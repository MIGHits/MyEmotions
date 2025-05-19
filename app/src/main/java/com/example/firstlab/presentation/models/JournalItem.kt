package com.example.firstlab.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JournalItem(
    val time: String,
    val name: String,
    val icon: Int,
    val background: Int,
    val color: Int
) : Parcelable
