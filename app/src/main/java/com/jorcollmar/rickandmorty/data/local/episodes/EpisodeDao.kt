package com.jorcollmar.rickandmorty.data.local.episodes

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface EpisodeDao {
    @Upsert
    suspend fun addEpisodes(list: List<EpisodeEntity>)

    @Query("Select * from EpisodeEntity")
    fun pagingSource(): PagingSource<Int, EpisodeEntity>

    @Query("Delete from EpisodeEntity")
    suspend fun clearCache()
}