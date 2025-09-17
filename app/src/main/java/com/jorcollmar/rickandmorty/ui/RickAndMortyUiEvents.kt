package com.jorcollmar.rickandmorty.ui

import com.jorcollmar.rickandmorty.domain.model.Character

sealed class RickAndMortyUiEvents {
    data object RefreshEpisodes : RickAndMortyUiEvents()

    data class SeeEpisodeDetails(
        val episodeName: String,
        val characterIds: List<Int>
    ) : RickAndMortyUiEvents()

    data class SeeCharacterDetails(val characterId: Int) : RickAndMortyUiEvents()

    data class ExportCharacterDetails(val character: Character) : RickAndMortyUiEvents()
}

sealed class RickAndMortyUiState {
    data object Error : RickAndMortyUiState()

    data object Loading : RickAndMortyUiState()

    data class Success(val character: Character) : RickAndMortyUiState()
}