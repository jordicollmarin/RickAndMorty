package com.jorcollmar.rickandmorty.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.jorcollmar.rickandmorty.data.local.RickAndMortyDatabase
import com.jorcollmar.rickandmorty.data.local.episodes.EpisodeEntity
import com.jorcollmar.rickandmorty.data.mapper.toEpisodeEntity
import com.jorcollmar.rickandmorty.data.mapper.toPagingEntity

const val PAGING_KEY = "paging_key"
private const val FIRST_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class RickAndMortyRemoteMediator(
    private val rickAndMortyApi: RickAndMortyApi,
    private val rickAndMortyDatabase: RickAndMortyDatabase
) : RemoteMediator<Int, EpisodeEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val nextPage = rickAndMortyDatabase.getPagingDao().getNext(PAGING_KEY)

                    if (nextPage == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    nextPage
                }
            }

            val response = rickAndMortyApi.getEpisodes(page)
            val episodes = response.results
            val pagingDto = response.info

            rickAndMortyDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    rickAndMortyDatabase.getEpisodeDao().clearCache()
                    rickAndMortyDatabase.getPagingDao().clearPaging()
                }

                rickAndMortyDatabase.getPagingDao().addPaging(pagingDto.toPagingEntity())

                rickAndMortyDatabase.getEpisodeDao()
                    .addEpisodes(episodes.map { it.toEpisodeEntity() })
            }

            MediatorResult.Success(endOfPaginationReached = pagingDto.next == null)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }
}