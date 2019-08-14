package com.elihimas.moviedatabase.presenters

import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.elihimas.moviedatabase.api.MoviesDatabaseRetrofit
import com.elihimas.moviedatabase.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSource(
    private val moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
    private val genreId: Int,
    private val compositeDisposable: CompositeDisposable,
    private val errorCallback: (cause: Throwable) -> Unit
) :
    PageKeyedDataSource<Int, Movie>() {

    private companion object {
        const val FIRST_PAGE = 1
    }

    override fun loadInitial(params: LoadInitialParams<Int>, loadInitialCallback: LoadInitialCallback<Int, Movie>) {
        loadPage(FIRST_PAGE) { movies ->
            loadInitialCallback.onResult(movies, -1, 2)
        }
    }

    private fun loadPage(page: Int, callback: (movies: List<Movie>) -> Unit) {
        val onSuccess = fun(movies: List<Movie>) {
            callback(movies)
        }

        val onFailure = fun(cause: Throwable) {
            errorCallback(cause)
        }

        compositeDisposable.add(
            moviesDatabaseRetrofit.listMovies(genreId, page).map { response ->
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
