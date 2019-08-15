package com.elihimas.moviedatabase.presenters

import androidx.paging.PageKeyedDataSource
import com.elihimas.moviedatabase.api.MoviesDatabaseRetrofit
import com.elihimas.moviedatabase.fragments.BaseView
import com.elihimas.moviedatabase.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalStateException

class MoviesDataSource private constructor(
    private val moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
    private val compositeDisposable: CompositeDisposable,
    private val view: BaseView?,
    private val errorCallback: (cause: Throwable) -> Unit
) :
    PageKeyedDataSource<Int, Movie>() {

    private var genreId: Int? = null
    private var searchQuery: String? = null

    constructor(
        moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
        compositeDisposable: CompositeDisposable,
        errorCallback: (cause: Throwable) -> Unit,
        view: BaseView?,
        genreId: Int
    ) : this(moviesDatabaseRetrofit, compositeDisposable, view, errorCallback) {
        this.genreId = genreId
    }

    constructor(
        moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
        compositeDisposable: CompositeDisposable,
        errorCallback: (cause: Throwable) -> Unit,
        view: BaseView?,
        searchQuery: String
    ) : this(moviesDatabaseRetrofit, compositeDisposable, view, errorCallback) {
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
            moviesDisposable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{
                    view?.showLoading()
                }
                .doFinally{
                    view?.hideLoading()
                }
                .map { response ->
                    response.movies
                }
                .subscribe(onSuccess, onFailure)
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
