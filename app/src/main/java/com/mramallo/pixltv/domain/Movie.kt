package com.mramallo.pixltv.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val releaseDate: String,
    val poster: String,
    val summary: String
): Parcelable
