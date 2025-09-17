package com.jorcollmar.rickandmorty.ui

import kotlinx.serialization.Serializable

sealed interface RickAndMortyRouter {
    val name: String

    @Serializable
    data object EpisodesList : RickAndMortyRouter {
        override val name: String = "episodes_list"
    }

    @Serializable
    data class CharactersList(
        val episodeName: String,
        val characterIds: List<Int>
    ) :
        RickAndMortyRouter {
        override val name: String = "characters_list"
    }

    @Serializable
    data class CharacterDetails(val characterId: Int) : RickAndMortyRouter {
        override val name: String = "character_details"
    }
}