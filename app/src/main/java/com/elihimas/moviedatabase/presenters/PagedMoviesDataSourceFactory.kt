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
    private val errorCallback: (Throwable) -> Unit
) :
    DataSource.Factory<Int, Movie>() {

    private companion object {
        const val PAGE_SIZE = 20
    }

    private var genreId: Int? = null
    private var searchQuery: String? = null

    private val compositeDisposable = CompositeDisposable()

    constructor(
        moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
        view: BaseView?,
        genreId: Int,
        errorCallback: (Throwable) -> Unit
    ) : this(moviesDatabaseRetrofit, view, errorCallback) {
        this.genreId = genreId
    }

    constructor(
        moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
        view: BaseView?,
        searchQuery: String,
        errorCallback: (Throwable) -> Unit
    ) : this(moviesDatabaseRetrofit, view, errorCallback) {
        this.searchQuery = searchQuery
    }


    val moviesPagedListObservable by lazy {
        RxPagedListBuilder(
            this,
            PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build()
        ).buildObservable()
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
