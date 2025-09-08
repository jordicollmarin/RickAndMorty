package com.jorcollmar.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jorcollmar.rickandmorty.data.local.episodes.Converters
import com.jorcollmar.rickandmorty.data.local.episodes.EpisodeDao
import com.jorcollmar.rickandmorty.data.local.episodes.EpisodeEntity
import com.jorcollmar.rickandmorty.data.local.paging.PagingDao
import com.jorcollmar.rickandmorty.data.local.paging.PagingEntity

@Database(
    version = 1,
    entities = [EpisodeEntity::class, PagingEntity::class],
    exportSchema = false
)
@TypeConverters(value = [Converters::class])
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun getEpisodeDao(): EpisodeDao

    abstract fun getPagingDao(): PagingDao
}