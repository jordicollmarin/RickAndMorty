package com.jorcollmar.rickandmorty.domain

import com.jorcollmar.rickandmorty.data.remote.RickAndMortyApi
import com.jorcollmar.rickandmorty.domain.mapper.toCharacter
import com.jorcollmar.rickandmorty.domain.model.Character
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi
) {
    suspend fun getCharacterDetails(characterId: Int): Result<Character> = try {
        Result.success(api.getCharacter(characterId).toCharacter())
    } catch (e: Exception) {
        Result.failure(e)
    }
}