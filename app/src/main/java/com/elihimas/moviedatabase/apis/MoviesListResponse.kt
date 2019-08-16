package com.elihimas.moviedatabase.apis

import com.elihimas.moviedatabase.model.Movie
import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    val page: Long,
    @SerializedName("total_pages") val totalPages: Long,
    @SerializedName("results") val movies: List<Movie>
)

