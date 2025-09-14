package com.jorcollmar.rickandmorty.domain

import androidx.paging.Pager
import androidx.paging.map
import com.jorcollmar.rickandmorty.data.local.episodes.EpisodeEntity
import com.jorcollmar.rickandmorty.domain.mapper.toEpisode
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val pager: Pager<Int, EpisodeEntity>
) {
    fun getEpisodes() = pager.flow.map { pagingData ->
        pagingData.map { it.toEpisode() }
    }
}