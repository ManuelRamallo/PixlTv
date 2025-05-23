package com.mramallo.pixltv.data.networking.dto

import com.google.gson.annotations.SerializedName

data class ResultMoviesDto(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieDto>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)