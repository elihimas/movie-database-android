package com.elihimas.moviedatabase.apis

import com.elihimas.moviedatabase.model.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesDatabaseRetrofit {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "f312161c6bec26d768aaaca97ad5a8b5"
    }

    @GET("discover/movie")
    fun listMoviesByGenre(@Query("with_genres") genre: Int, @Query("page") page: Int): Single<MoviesListResponse>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String, @Query("page") page: Int): Single<MoviesListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path(value = "movie_id") movieId: Long): Single<Movie>
}