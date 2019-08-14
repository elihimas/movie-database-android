package com.elihimas.moviedatabase.presenters

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.elihimas.moviedatabase.api.MoviesDatabaseRetrofit
import com.elihimas.moviedatabase.model.Movie
import io.reactivex.disposables.CompositeDisposable

class PagedMoviesDataSourceFactory(
    private val moviesDatabaseRetrofit: MoviesDatabaseRetrofit,
    private val genreId: Int,
    onSuccess: (PagedList<Movie>) -> Unit,
    private val errorCallback: (Throwable) -> Unit
) :
    DataSource.Factory<Int, Movie>() {


    private val compositeDisposable = CompositeDisposable()

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
        MoviesDataSource(moviesDatabaseRetrofit, genreId, compositeDisposable, errorCallback)

    fun onDestroy() {
        compositeDisposable.dispose()
    }

}
