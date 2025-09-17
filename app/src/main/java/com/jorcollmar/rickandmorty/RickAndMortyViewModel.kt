package com.jorcollmar.rickandmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jorcollmar.rickandmorty.domain.CharacterRepository
import com.jorcollmar.rickandmorty.domain.EpisodeRepository
import com.jorcollmar.rickandmorty.domain.model.Character
import com.jorcollmar.rickandmorty.ui.RickAndMortyUiEvents
import com.jorcollmar.rickandmorty.ui.RickAndMortyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _uiStateCharacter =
        MutableStateFlow<RickAndMortyUiState>(RickAndMortyUiState.Loading)
    val uiStateCharacter: StateFlow<RickAndMortyUiState> = _uiStateCharacter

    private val _uiEvents = MutableSharedFlow<RickAndMortyUiEvents>()
    val uiEvents: SharedFlow<RickAndMortyUiEvents> = _uiEvents

    fun onEpisodeClicked(
        episodeName: String,
        characterIds: List<Int>
    ) = viewModelScope.launch {
        _uiEvents.emit(RickAndMortyUiEvents.SeeEpisodeDetails(episodeName, characterIds))
    }

    fun onPullToRefresh() = viewModelScope.launch {
        _uiEvents.emit(RickAndMortyUiEvents.RefreshEpisodes)
    }

    fun onCharacterIdClicked(
        characterId: Int
    ) = viewModelScope.launch {
        _uiEvents.emit(RickAndMortyUiEvents.SeeCharacterDetails(characterId))
    }

    fun onExportCharacterClicked(
        character: Character
    ) = viewModelScope.launch {
        _uiEvents.emit(RickAndMortyUiEvents.ExportCharacterDetails(character))
    }

    fun loadCharacterDetails(
        characterId: Int
    ) = viewModelScope.launch {
        characterRepository.getCharacterDetails(characterId).fold(
            onSuccess = { character ->
                _uiStateCharacter.value = RickAndMortyUiState.Success(character)
            },
            onFailure = {
                _uiStateCharacter.value = RickAndMortyUiState.Error
            }
        )
    }
}