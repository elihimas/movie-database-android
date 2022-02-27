package com.elihimas.moviedatabase.apis

import androidx.paging.PageKeyedDataSource
import com.elihimas.moviedatabase.fragments.BaseView
import com.elihimas.moviedatabase.isValidQuery
import com.elihimas.moviedatabase.model.Movie
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesDataSource private constructor(
    private val moviesDatabaseService: MoviesDatabaseService,
    private val compositeDisposable: CompositeDisposable,
    private val view: BaseView?,
    private val callbacks: LoadItemsCallbacks
) :
    PageKeyedDataSource<Int, Movie>() {

    companion object {
        private const val FIRST_PAGE = 0
        const val MIN_QUERY_LEN = 3
    }


    private var genreId: Int? = null
    private var searchQuery: String? = null

    constructor(
        moviesDatabaseService: MoviesDatabaseService,
        compositeDisposable: CompositeDisposable,
        callbacks: LoadItemsCallbacks,
        view: BaseView?,
        genreId: Int
    ) : this(moviesDatabaseService, compositeDisposable, view, callbacks) {
        this.genreId = genreId
    }

    constructor(
        moviesDatabaseService: MoviesDatabaseService,
        compositeDisposable: CompositeDisposable,
        callbacks: LoadItemsCallbacks,
        view: BaseView?,
        searchQuery: String
    ) : this(moviesDatabaseService, compositeDisposable, view, callbacks) {
        this.searchQuery = searchQuery
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        loadInitialCallback: LoadInitialCallback<Int, Movie>
    ) {
        loadPage(FIRST_PAGE) { movies ->
            loadInitialCallback.onResult(movies, -1, FIRST_PAGE + 1)

            if (movies.isEmpty() && searchQuery.isValidQuery()) {
                callbacks.onNothingFound()
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        loadPage(params.key) { movies ->
            callback.onResult(movies, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //nothing to do
    }

    private fun loadPage(zeroBasedPage: Int, callback: (movies: List<Movie>) -> Unit) {
        val onSuccess = fun(movies: List<Movie>) {
            callback(movies)
        }

        val onFailure = fun(cause: Throwable) {
            callbacks.onError(cause)
        }

        val moviesDatabasePageIndex = zeroBasedPage + 1

        val moviesDisposable = genreId?.let { genreId ->
            moviesDatabaseService.listMoviesByGenre(genreId, moviesDatabasePageIndex)
        } ?: searchQuery?.let { searchQuery ->
            if (searchQuery.isValidQuery()) {
                moviesDatabaseService.searchMovies(searchQuery, moviesDatabasePageIndex)
            } else {
                Single.just(MoviesListResponse(1, 0, listOf()))
            }
        } ?: throw IllegalStateException("nor genreId nor searchQuery defined")

        compositeDisposable.add(
            moviesDisposable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (zeroBasedPage == FIRST_PAGE) {
                        view?.showLoading()
                    }
                }
                .doFinally {
                    if (zeroBasedPage == FIRST_PAGE) {
                        view?.hideLoading()
                    }
                }
                .map { response ->
                    response.movies
                }
                .subscribe(onSuccess, onFailure)
        )
    }

}
