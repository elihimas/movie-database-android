package com.elihimas.moviedatabase.api

import com.google.gson.annotations.SerializedName

data class ListMoviesResponse(
    val page: Long,
    @SerializedName("total_pages") val totalPages: Long,
    @SerializedName("movies") val movies: List<MovieResponse>
)

data class MovieResponse(val title: String)
