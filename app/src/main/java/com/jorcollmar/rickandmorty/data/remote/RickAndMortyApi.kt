package com.jorcollmar.rickandmorty.data.remote

import com.jorcollmar.rickandmorty.data.remote.model.EpisodesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int?
    ) : EpisodesDto

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") characterId: Int
    )

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}