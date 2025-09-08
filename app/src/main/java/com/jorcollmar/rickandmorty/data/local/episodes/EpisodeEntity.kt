package com.jorcollmar.rickandmorty.data.local.episodes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EpisodeEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val airDate: String,
    val code: String,
    val characters: List<String>
)