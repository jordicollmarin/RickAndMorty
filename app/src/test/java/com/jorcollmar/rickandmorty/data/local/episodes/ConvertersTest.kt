package com.jorcollmar.rickandmorty.data.local.episodes

import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    @Test
    fun stringListConvertsToString() {
        // Given
        val list = listOf("Test 1", "Test 2")
        val expected = "Test 1,Test 2"

        // When
        val result = Converters().fromListStringToString(list)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun fromStringToListStrings() {
        // Given
        val data = "Test 1,Test 2"
        val expected = listOf("Test 1", "Test 2")

        // When
        val result = Converters().fromStringToListStrings(data)

        // Then
        assertEquals(expected, result)
    }
}