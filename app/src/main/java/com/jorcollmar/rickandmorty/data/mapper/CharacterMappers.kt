package com.jorcollmar.rickandmorty.data.mapper

import com.jorcollmar.rickandmorty.data.remote.model.CharacterDetailsDto
import com.jorcollmar.rickandmorty.domain.model.Character

fun CharacterDetailsDto.toCharacter() = Character(
    id = id,
    imageUrl = image,
    name = name,
    status = status,
    species = species,
    originName = origin.name,
    appearanceCount = episode.size
)