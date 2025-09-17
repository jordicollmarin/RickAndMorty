package com.jorcollmar.rickandmorty.mocks

import com.jorcollmar.rickandmorty.data.local.episodes.EpisodeEntity
import com.jorcollmar.rickandmorty.data.local.paging.PagingEntity
import com.jorcollmar.rickandmorty.data.remote.PAGING_KEY
import com.jorcollmar.rickandmorty.data.remote.model.CharacterDetailsDto
import com.jorcollmar.rickandmorty.data.remote.model.EpisodeDto
import com.jorcollmar.rickandmorty.data.remote.model.OriginDetailsDto
import com.jorcollmar.rickandmorty.data.remote.model.PagingDto
import com.jorcollmar.rickandmorty.domain.model.Character
import com.jorcollmar.rickandmorty.domain.model.Episode
import java.time.LocalDate

fun generatePagingDto(
    count: Int = PAGING_COUNT,
    pages: Int = PAGING_PAGES_COUNT,
    next: String? = PAGING_NEXT,
    prev: String? = PAGING_PREV
) = PagingDto(count, pages, next, prev)

fun generatePagingEntity(
    id: String = PAGING_KEY,
    next: Int? = PAGING_NEXT_NUMBER,
    prev: Int? = PAGING_PREV_NUMBER
) = PagingEntity(id, next, prev)

fun generateEpisodeDto(
    id: Int = ID,
    name: String = NAME,
    airDate: String = EPISODE_AIR_DATE,
    code: String = EPISODE_CODE,
    characters: List<String> = listOf(CHARACTER_1, CHARACTER_2)
) = EpisodeDto(id, name, airDate, code, characters)

fun generateEpisodeEntity(
    id: Int = ID,
    name: String = NAME,
    airDate: String = EPISODE_AIR_DATE,
    code: String = EPISODE_CODE,
    characters: List<String> = listOf(CHARACTER_1, CHARACTER_2)
) = EpisodeEntity(id, name, airDate, code, characters)

fun generateEpisode(
    id: Int = ID,
    name: String = NAME,
    airDate: LocalDate = LocalDate.now(),
    code: String = EPISODE_CODE,
    characters: List<Int> = listOf(1, 2)
) = Episode(id, name, airDate, code, characters)

fun generateCharacterDetailsDto(
    id: Int = ID,
    name: String = NAME,
    status: String = STATUS,
    species: String = SPECIES,
    image: String = IMAGE,
    origin: OriginDetailsDto = generateOriginDetailsDto(),
    episode: List<String> = listOf(EPISODE_1, EPISODE_2)
) = CharacterDetailsDto(
    id = id,
    name = name,
    status = status,
    species = species,
    image = image,
    origin = origin,
    episode = episode
)

fun generateOriginDetailsDto(
    name: String = ORIGIN_NAME,
    url: String = ORIGIN_URL,
) = OriginDetailsDto(
    name = name,
    url = url
)

fun generateCharacter(
    id: Int = ID,
    name: String = NAME,
    status: String = STATUS,
    species: String = SPECIES,
    image: String = IMAGE,
    originName: String = ORIGIN_NAME,
    episodeCount: Int = 2
) = Character(
    id = id,
    imageUrl = image,
    name = name,
    status = status,
    species = species,
    originName = originName,
    appearanceCount = episodeCount
)