package com.elihimas.moviedatabase.presenters

import androidx.paging.PagedList
import com.elihimas.moviedatabase.apis.APIFactory
import com.elihimas.moviedatabase.apis.LoadItemsCallbacks
import com.elihimas.moviedatabase.apis.PagedMoviesDataSourceFactory
import com.elihimas.moviedatabase.contracts.MoviesListContract
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie

class ListMoviesPresenter :
    AbstractPresenter<MoviesListContract.View>(),
    MoviesListContract.Presenter,
    LoadItemsCallbacks {

    private val moviesDatabaseService = APIFactory.createMoviesDatabaseService()
    private var pagedMoviesDataSourceFactory: PagedMoviesDataSourceFactory? = null

    private fun onSuccess(moviesPagedList: PagedList<Movie>) {
        view?.showMovies(moviesPagedList)
    }

    override fun onNothingFound() {
        view?.showNothingFound()
    }

    override fun onError(cause: Throwable) {
        view?.hideLoading()
        view?.showError(cause)
    }

    override fun loadGenreMovies(genre: Genre) {
        val genreId = genre.getIdOnMoviesDatabase()
        pagedMoviesDataSourceFactory = PagedMoviesDataSourceFactory(moviesDatabaseService, view, genreId, this)
            .also { factory ->
                addDisposable(factory.moviesPagedListObservable.subscribe(::onSuccess))
            }
    }


    override fun searchMovies(query: String) {
        pagedMoviesDataSourceFactory = PagedMoviesDataSourceFactory(moviesDatabaseService, view, query, this)
            .also { factory ->
                addDisposable(factory.moviesPagedListObservable.subscribe(::onSuccess))
            }
    }

    override fun onDestroy() {
        super.onDestroy()

        pagedMoviesDataSourceFactory?.onDestroy()
    }
}