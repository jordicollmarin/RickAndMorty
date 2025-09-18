package com.jorcollmar.rickandmorty.domain

import com.jorcollmar.rickandmorty.data.remote.RickAndMortyApi
import com.jorcollmar.rickandmorty.mocks.ID
import com.jorcollmar.rickandmorty.mocks.generateCharacter
import com.jorcollmar.rickandmorty.mocks.generateCharacterDetailsDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterRepositoryTest {
    private val api: RickAndMortyApi = mockk()
    private val repository = CharacterRepository(api)

    @Test
    fun repositoryReturnsSuccess() = runTest {
        // Given
        val characterId = ID
        val characterDetailsDto = generateCharacterDetailsDto()
        val character = generateCharacter()
        coEvery { api.getCharacter(characterId) } returns characterDetailsDto

        // When
        val response = repository.getCharacterDetails(characterId)

        // Then
        assertEquals(Result.success(character), response)
    }

    @Test
    fun repositoryReturnsError() = runTest {
        // Given
        val characterId = ID
        val exception = Exception()
        coEvery { api.getCharacter(characterId) } throws exception

        // When
        val response = repository.getCharacterDetails(characterId)

        // Then
        assertEquals(Result.failure<Exception>(exception), response)
    }
}