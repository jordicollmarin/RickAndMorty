package com.jorcollmar.rickandmorty.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object Date {
    fun stringToLocalDate(value: String, pattern: String): LocalDate? {
        return try {
            if (value.isNotEmpty()) {
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
                LocalDate.parse(value, formatter)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}