package com.jorcollmar.rickandmorty.data.remote.model

import kotlinx.serialization.SerialName

data class EpisodesDto(
    val info: PagingDto,
    val results: List<EpisodeDto>
)

data class EpisodeDto(
    val id: Int,
    val name: String,
    @SerialName("air_date")
    val airDate: String,
    @SerialName("episode")
    val code: String,
    val characters: List<String>
)