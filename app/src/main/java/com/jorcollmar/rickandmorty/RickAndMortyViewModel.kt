package com.jorcollmar.rickandmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jorcollmar.rickandmorty.domain.CharacterRepository
import com.jorcollmar.rickandmorty.domain.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    episodeRepository: EpisodeRepository,
    characterRepository: CharacterRepository
) : ViewModel() {

    val episodes = episodeRepository.getEpisodes().cachedIn(viewModelScope)

    // TODO Navigation - Get character details from navigation
    // val characterDetails = characterRepository.getCharacterDetails(4).cachedIn(viewModelScope)
}