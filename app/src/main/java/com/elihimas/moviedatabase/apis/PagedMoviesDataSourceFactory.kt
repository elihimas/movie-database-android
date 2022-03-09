package com.elihimas.moviedatabase.apis

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.elihimas.moviedatabase.ui.fragments.BaseView
import com.elihimas.moviedatabase.model.Movie
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalStateException

class PagedMoviesDataSourceFactory private constructor(
    private val moviesDatabaseService: MoviesDatabaseService,
    private val view: BaseView?,
    private val callbacks: LoadItemsCallbacks
) :
    DataSource.Factory<Int, Movie>() {

    private companion object {
        const val PAGE_SIZE = 20
    }

    private var genreId: Int? = null
    private var searchQuery: String? = null

    private val compositeDisposable = CompositeDisposable()

    constructor(
        moviesDatabaseService: MoviesDatabaseService,
        view: BaseView?,
        genreId: Int,
        callbacks: LoadItemsCallbacks
    ) : this(moviesDatabaseService, view, callbacks) {
        this.genreId = genreId
    }

    constructor(
        moviesDatabaseService: MoviesDatabaseService,
        view: BaseView?,
        searchQuery: String,
        callbacks: LoadItemsCallbacks
    ) : this(moviesDatabaseService, view, callbacks) {
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
            MoviesDataSource(moviesDatabaseService, compositeDisposable, callbacks, view, genreId)
        } ?: searchQuery?.let { searchQuery ->
            MoviesDataSource(moviesDatabaseService, compositeDisposable, callbacks, view, searchQuery)
        } ?: throw IllegalStateException("nor genreId nor search query defined")

    fun onDestroy() {
        compositeDisposable.dispose()
    }

}
