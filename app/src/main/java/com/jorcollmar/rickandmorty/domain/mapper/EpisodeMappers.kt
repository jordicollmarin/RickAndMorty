package com.jorcollmar.rickandmorty.domain.mapper

import com.jorcollmar.rickandmorty.data.local.episodes.EpisodeEntity
import com.jorcollmar.rickandmorty.data.remote.RickAndMortyApi.Companion.EPISODE_AIR_DATE_API_FORMAT
import com.jorcollmar.rickandmorty.domain.model.Episode
import com.jorcollmar.rickandmorty.domain.util.Date

fun EpisodeEntity.toEpisode() = Episode(
    id = id,
    name = name,
    airDate = Date.stringToDate(value = airDate, pattern = EPISODE_AIR_DATE_API_FORMAT),
    code = code,
    characters = characters
)