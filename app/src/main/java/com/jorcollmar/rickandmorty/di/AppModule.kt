package com.jorcollmar.rickandmorty.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.room.Room
import com.jorcollmar.rickandmorty.data.local.RickAndMortyDatabase
import com.jorcollmar.rickandmorty.data.local.episodes.EpisodeEntity
import com.jorcollmar.rickandmorty.data.remote.RickAndMortyApi
import com.jorcollmar.rickandmorty.data.remote.RickAndMortyRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@OptIn(ExperimentalPagingApi::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): RickAndMortyApi {
        return Retrofit
            .Builder()
            .baseUrl(RickAndMortyApi.BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(
                RickAndMortyApi::
                class.java
            )
    }

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            "rickandmorty.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyPager(
        rickAndMortyDatabase: RickAndMortyDatabase,
        rickAndMortyApi: RickAndMortyApi
    ): Pager<Int, EpisodeEntity> {
        return Pager(
            config = androidx.paging.PagingConfig(pageSize = 20),
            remoteMediator = RickAndMortyRemoteMediator(
                rickAndMortyApi,
                rickAndMortyDatabase
            ),
            pagingSourceFactory = {
                rickAndMortyDatabase.getEpisodeDao().pagingSource()
            }
        )
    }
}