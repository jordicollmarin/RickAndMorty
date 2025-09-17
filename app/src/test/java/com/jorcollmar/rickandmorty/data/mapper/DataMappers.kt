package com.jorcollmar.rickandmorty.data.mapper

import com.jorcollmar.rickandmorty.mocks.PAGING_NEXT
import com.jorcollmar.rickandmorty.mocks.generateEpisodeDto
import com.jorcollmar.rickandmorty.mocks.generateEpisodeEntity
import com.jorcollmar.rickandmorty.mocks.generatePagingDto
import com.jorcollmar.rickandmorty.mocks.generatePagingEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class DataMappers {

    @Test
    fun episodeDtoToEpisodeEntity() {
        // Given
        val dto = generateEpisodeDto()
        val expected = generateEpisodeEntity()

        // When
        val result = dto.toEpisodeEntity()

        // Then
        assert(result == expected)
    }

    @Test
    fun pagingDtoToPagingEntity() {
        // Given
        val dto = generatePagingDto(
            next = PAGING_NEXT,
            prev = null
        )
        val expected = generatePagingEntity(
            next = 1,
            prev = null
        )

        // When
        val result = dto.toPagingEntity()

        // Then
        assertEquals(expected, result)
    }
}