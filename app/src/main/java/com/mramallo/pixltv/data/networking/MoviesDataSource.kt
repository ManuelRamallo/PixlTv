package com.mramallo.pixltv.data.networking

import com.mramallo.pixltv.data.networking.dto.RemoteMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesDataSource {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMovies(
        @Query("api_key") apiKey: String
    ): RemoteMoviesDto

}