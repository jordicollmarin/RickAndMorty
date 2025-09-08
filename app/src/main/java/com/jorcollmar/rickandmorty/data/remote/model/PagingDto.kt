package com.jorcollmar.rickandmorty.data.remote.model

data class PagingDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)