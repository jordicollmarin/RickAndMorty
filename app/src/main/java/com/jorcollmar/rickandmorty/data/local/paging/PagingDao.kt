package com.jorcollmar.rickandmorty.data.local.paging

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PagingDao {
    @Upsert
    suspend fun addPaging(pagingEntity: PagingEntity)

    @Query("SELECT next FROM PagingEntity WHERE id = :key")
    suspend fun getNext(key: String): Int?

    @Query("Delete from PagingEntity")
    suspend fun clearPaging()
}