package com.jorcollmar.rickandmorty.domain

import com.jorcollmar.rickandmorty.domain.mapper.toCharacter
import com.jorcollmar.rickandmorty.data.remote.RickAndMortyApi
import com.jorcollmar.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi
) {
    fun getCharacterDetails(characterId: Int): Flow<Character> =
        flow { api.getCharacter(characterId).toCharacter() }
}