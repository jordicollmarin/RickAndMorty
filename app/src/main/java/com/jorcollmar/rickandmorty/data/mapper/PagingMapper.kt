package com.jorcollmar.rickandmorty.data.mapper

import com.jorcollmar.rickandmorty.data.local.paging.PagingEntity
import com.jorcollmar.rickandmorty.data.remote.PAGING_KEY
import com.jorcollmar.rickandmorty.data.remote.model.PagingDto

fun PagingDto.toPagingEntity() = PagingEntity(
    id = PAGING_KEY,
    next = next?.last()?.digitToInt(),
    prev = next?.last()?.digitToInt()
)
