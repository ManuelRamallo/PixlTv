package com.mramallo.pixltv.data.mappers

import com.mramallo.pixltv.data.networking.dto.MovieDto
import com.mramallo.pixltv.domain.Movie

fun MovieDto.toMovie() = Movie(
    title = title,
    releaseDate = release_date,
    poster = "https://image.tmdb.org/t/p/w185/$poster_path",
    summary = overview,
    backdrop = backdrop_path?.let { "https://image.tmdb.org/t/p/w500/$it" } ?: ""
)