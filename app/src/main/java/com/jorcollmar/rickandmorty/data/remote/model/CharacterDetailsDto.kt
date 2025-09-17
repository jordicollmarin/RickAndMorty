package com.jorcollmar.rickandmorty.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailsDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: OriginDetailsDto,
    val episode: List<String>
)

@Serializable
data class OriginDetailsDto(
    val name: String,
    val url: String,
)