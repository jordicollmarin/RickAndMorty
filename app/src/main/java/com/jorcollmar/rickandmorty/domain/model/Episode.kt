package com.jorcollmar.rickandmorty.domain.model

import java.time.LocalDate

data class Episode(
    val id: Int,
    val name: String,
    val airDate: LocalDate,
    val code: String,
    val characters: List<String>
)