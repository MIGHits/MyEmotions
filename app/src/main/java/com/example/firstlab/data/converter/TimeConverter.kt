package com.example.firstlab.data.converter

import androidx.room.TypeConverter
import java.time.LocalTime

class TimeConverter {
    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.toString()
    }

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it) }
    }
}