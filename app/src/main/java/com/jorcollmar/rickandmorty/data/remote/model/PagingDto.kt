package com.jorcollmar.rickandmorty.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PagingDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)