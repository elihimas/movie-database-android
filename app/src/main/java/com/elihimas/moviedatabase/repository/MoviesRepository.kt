package com.elihimas.moviedatabase.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elihimas.moviedatabase.apis.MoviesDatabaseService
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.paging.MoviesPagingSource
import com.elihimas.moviedatabase.paging.SearchMoviesPagingSource

class MoviesRepository(private val moviesDatabaseService: MoviesDatabaseService) {

    fun searchMovies(query: String) = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SearchMoviesPagingSource(
                query,
                moviesDatabaseService
            )
        }
    ).flow

    fun loadGenreMovies(genre: Genre) = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MoviesPagingSource(
                genre.getIdOnMoviesDatabase(),
                moviesDatabaseService
            )
        }
    ).flow

    private companion object {
        const val pageSize = 20
    }
}
