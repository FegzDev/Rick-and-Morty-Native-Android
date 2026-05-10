package com.lawpavilion.rick_and_morty.core.data.local.converters

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toList(raw: String): List<String> {
        return if (raw.isBlank()) emptyList() else raw.split(",")
    }
}