package com.jorcollmar.rickandmorty.data.mapper

import com.jorcollmar.rickandmorty.data.local.episodes.EpisodeEntity
import com.jorcollmar.rickandmorty.data.remote.model.EpisodeDto
import com.jorcollmar.rickandmorty.domain.model.Episode

fun EpisodeDto.toEpisodeEntity() = EpisodeEntity(
    id = id,
    name = name,
    airDate = airDate,
    code = code,
    characters = characters
)

fun EpisodeEntity.toEpisode() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    code = code,
    characters = characters
)