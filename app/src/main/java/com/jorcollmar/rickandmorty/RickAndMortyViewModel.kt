package com.jorcollmar.rickandmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jorcollmar.rickandmorty.domain.CharacterRepository
import com.jorcollmar.rickandmorty.domain.EpisodeRepository
import com.jorcollmar.rickandmorty.ui.character.CharacterDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    episodeRepository: EpisodeRepository,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val episodes = episodeRepository.getEpisodes().cachedIn(viewModelScope)

    private val _uiStateCharacter = MutableStateFlow<CharacterDetailsUiState>(
        CharacterDetailsUiState.Loading
    )
    val uiStateCharacter: StateFlow<CharacterDetailsUiState> = _uiStateCharacter

    fun loadCharacterDetails(
        characterId: Int
    ) = viewModelScope.launch {
        characterRepository.getCharacterDetails(characterId).fold(
            onSuccess = { character ->
                _uiStateCharacter.value = CharacterDetailsUiState.Success(character)
            },
            onFailure = {
                _uiStateCharacter.value = CharacterDetailsUiState.LoadingError
            }
        )
    }

    fun showExportCharacterError() {
        _uiStateCharacter.value = CharacterDetailsUiState.ExportError
    }
}