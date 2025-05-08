package com.mramallo.pixltv.domain

enum class Category(val id: String) {
    POPULAR("popularity.desc"),
    NEW("primary_release_date.desc"),
    VOTES("vote_average.desc"),
    REVENUE("revenue.desc")
}