package com.elihimas.moviedatabase.presenters

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.elihimas.moviedatabase.api.MoviesDatabaseRetrofit
import com.elihimas.moviedatabase.fragments.BaseView
import com.elihimas.moviedatabase.model.Movie
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalStateException

class PagedMoviesDataSourceFactory private constructor(
    private val moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
    private val view: BaseView?,
    onSuccess: (PagedList<Movie>) -> Unit,
    private val errorCallback: (Throwable) -> Unit
) :
    DataSource.Factory<Int, Movie>() {

    private var genreId: Int? = null
    private var searchQuery: String? = null

    private val compositeDisposable = CompositeDisposable()

    constructor(
        moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
        view: BaseView?,
        genreId: Int,
        onSuccess: (PagedList<Movie>) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) : this(moviesDatabaseRetrofit, view, onSuccess, errorCallback) {
        this.genreId = genreId
    }

    constructor(
        moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
        view: BaseView?,
        searchQuery: String,
        onSuccess: (PagedList<Movie>) -> Unit,
        errorCallback: (Throwable) -> Unit
    ) : this(moviesDatabaseRetrofit, view, onSuccess, errorCallback) {
        this.searchQuery = searchQuery
    }

    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .build()
        val moviesPagedListObservable =
            RxPagedListBuilder(
                this, config
            )
                .buildObservable()
        moviesPagedListObservable.subscribe(onSuccess)
    }

    override fun create(): DataSource<Int, Movie> =
        genreId?.let { genreId ->
            MoviesDataSource(moviesDatabaseRetrofit, compositeDisposable, errorCallback, view, genreId)
        } ?: searchQuery?.let { searchQuery ->
            MoviesDataSource(moviesDatabaseRetrofit, compositeDisposable, errorCallback, view, searchQuery)
        } ?: throw IllegalStateException("nor genreId nor search query defined")

    fun onDestroy() {
        compositeDisposable.dispose()
    }

}
