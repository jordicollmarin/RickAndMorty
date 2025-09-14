package com.jorcollmar.rickandmorty.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object Date {
    fun stringToDate(value: String, pattern: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
        return LocalDate.parse(value, formatter)
    }
}