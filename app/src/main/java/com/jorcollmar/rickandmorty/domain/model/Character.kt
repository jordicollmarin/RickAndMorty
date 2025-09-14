package com.jorcollmar.rickandmorty.domain.model

data class Character(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val status: String,
    val species: String,
    val originName: String,
    val appearanceCount: Int
)
