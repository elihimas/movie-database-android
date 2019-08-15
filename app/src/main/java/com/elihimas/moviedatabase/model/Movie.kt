package com.elihimas.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String
)