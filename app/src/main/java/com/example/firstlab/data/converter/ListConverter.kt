package com.example.firstlab.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(data: String?): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }
}