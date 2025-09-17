package com.jorcollmar.rickandmorty.domain.util

import com.jorcollmar.rickandmorty.data.remote.RickAndMortyApi.Companion.EPISODE_AIR_DATE_API_FORMAT
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate


class DateTest {
    @Test
    fun stringToDateReturnsNullWhenDateIsEmpty() {
        // Given
        val stringDate = ""

        // When
        val result = Date.stringToLocalDate(stringDate, EPISODE_AIR_DATE_API_FORMAT)

        // Then
        assertEquals(null, result)
    }

    @Test
    fun stringToDateReturnsNullWhenExceptionIsThrown() {
        // Given
        val stringDate = "December 2, 2013"
        val pattern = "asdf"

        // When
        val result = Date.stringToLocalDate(stringDate, pattern)

        // Then
        assertEquals(null, result)
    }

    @Test
    fun stringToDateReturnsCorrectLocalDate() {
        // Given
        val stringDate = "September 17, 2025"
        val expected = LocalDate.parse("2025-09-17")

        // When
        val result = Date.stringToLocalDate(stringDate, EPISODE_AIR_DATE_API_FORMAT)

        // Then
        assertEquals(expected, result)
    }
}