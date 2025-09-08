package com.jorcollmar.rickandmorty.data.local.episodes

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListStringToString(list: List<String>): String = list.joinToString(SEPARATOR)

    @TypeConverter
    fun fromStringToListStrings(string: String): List<String> = string.split(SEPARATOR)

    companion object {
        private const val SEPARATOR = ","
    }
}