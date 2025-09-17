package com.jorcollmar.rickandmorty.domain.mapper

import com.jorcollmar.rickandmorty.mocks.CHARACTER_1
import com.jorcollmar.rickandmorty.mocks.CHARACTER_2
import com.jorcollmar.rickandmorty.mocks.generateCharacter
import com.jorcollmar.rickandmorty.mocks.generateCharacterDetailsDto
import com.jorcollmar.rickandmorty.mocks.generateEpisode
import com.jorcollmar.rickandmorty.mocks.generateEpisodeEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DomainMappers {
    @Test
    fun characterDetailsDtoToCharacter() {
        // Given
        val dto = generateCharacterDetailsDto()
        val expected = generateCharacter()

        // When
        val result = dto.toCharacter()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun episodeEntityToEpisode() {
        // Given
        val episodeAirDate = "September 17, 2025"
        val formattedDate = "2025-09-17"
        val dto = generateEpisodeEntity(
            airDate = episodeAirDate,
            characters = listOf(CHARACTER_1, CHARACTER_2)
        )
        val expected = generateEpisode(
            airDate = LocalDate.parse(formattedDate),
            characters = listOf(1, 2)
        )

        // When
        val result = dto.toEpisode()

        // Then
        assertEquals(expected, result)
    }
}