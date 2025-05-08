package com.mramallo.pixltv.data.networking

import com.mramallo.pixltv.data.mappers.toMovie
import com.mramallo.pixltv.domain.Category
import com.mramallo.pixltv.domain.Movie

class MoviesRepository(private val apiKey: String) {
    suspend fun getCategories(): Map<Category, List<Movie>> {
        return Category.entries.associateWith { category ->
            RemoteConnection
                .service
                .listPopularMovies(apiKey, category.id)
                .results.map { it.toMovie() }
        }
    }
}