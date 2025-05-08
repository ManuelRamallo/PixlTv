package com.mramallo.pixltv.data.networking

import com.mramallo.pixltv.data.networking.dto.ResultMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesDataSource {

    @GET("discover/movie")
    suspend fun listPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("vote_count.gte") voteCount: Int = 100
    ): ResultMoviesDto

}