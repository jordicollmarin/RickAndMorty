package com.jorcollmar.rickandmorty.data.local.paging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PagingEntity(
    @PrimaryKey
    val id: String,
    val next: Int?,
    val prev: Int?
)
