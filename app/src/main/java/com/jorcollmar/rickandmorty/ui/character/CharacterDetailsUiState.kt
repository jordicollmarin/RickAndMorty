package com.jorcollmar.rickandmorty.ui.character

import com.jorcollmar.rickandmorty.domain.model.Character

sealed class CharacterDetailsUiState {
    data object LoadingError : CharacterDetailsUiState()

    data object ExportError : CharacterDetailsUiState()

    data object Loading : CharacterDetailsUiState()

    data class Success(val character: Character) : CharacterDetailsUiState()
}