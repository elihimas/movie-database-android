package com.elihimas.moviedatabase.presenters

import androidx.paging.PageKeyedDataSource
import com.elihimas.moviedatabase.api.MoviesDatabaseRetrofit
import com.elihimas.moviedatabase.model.Movie
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalStateException

class MoviesDataSource private constructor(
    private val moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
    private val compositeDisposable: CompositeDisposable,
    private val errorCallback: (cause: Throwable) -> Unit
) :
    PageKeyedDataSource<Int, Movie>() {

    private var genreId: Int? = null
    private var searchQuery: String? = null

    constructor(
        moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
        compositeDisposable: CompositeDisposable,
        errorCallback: (cause: Throwable) -> Unit,
        genreId: Int
    ) : this(moviesDatabaseRetrofit, compositeDisposable, errorCallback) {
        this.genreId = genreId
    }


    constructor(
        moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
        compositeDisposable: CompositeDisposable,
        errorCallback: (cause: Throwable) -> Unit,
        searchQuery: String
    ) : this(moviesDatabaseRetrofit, compositeDisposable, errorCallback) {
        this.searchQuery = searchQuery
    }

    private companion object {
        const val FIRST_PAGE = 1
    }

    override fun loadInitial(params: LoadInitialParams<Int>, loadInitialCallback: LoadInitialCallback<Int, Movie>) {
        loadPage(FIRST_PAGE) { movies ->
            loadInitialCallback.onResult(movies, -1, FIRST_PAGE + 1)
        }
    }

    private fun loadPage(page: Int, callback: (movies: List<Movie>) -> Unit) {
        val onSuccess = fun(movies: List<Movie>) {
            callback(movies)
        }

        val onFailure = fun(cause: Throwable) {
            errorCallback(cause)
        }

        val moviesDisposable = genreId?.let { genreId ->
            moviesDatabaseRetrofit.listMoviesByGenre(genreId, page)
        } ?: searchQuery?.let { searchQuery ->
            moviesDatabaseRetrofit.searchMovies(searchQuery, page)
        } ?: throw IllegalStateException("nor genreId nor search query defined")

        compositeDisposable.add(
            moviesDisposable.map { response ->
                response.movies
            }.subscribe(onSuccess, onFailure)
        )
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        loadPage(params.key) { movies ->
            callback.onResult(movies, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //nothing to do
    }


}
