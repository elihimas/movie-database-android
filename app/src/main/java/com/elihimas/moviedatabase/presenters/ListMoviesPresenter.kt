package com.elihimas.moviedatabase.presenters

import androidx.paging.PagedList
import com.elihimas.moviedatabase.contracts.ListMoviesContract
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie

class ListMoviesPresenter :
    AbstractPresenter<ListMoviesContract.MoviesGenreView>(),
    ListMoviesContract.Presenter {

    private val moviesDatabaseRetrofit = APIFactory.createMoviesDatabaseRetrofit()
    private var pagedMoviesDataSourceFactory: PagedMoviesDataSourceFactory? = null

    private fun onSuccess(movies: PagedList<Movie>) {
        view?.hideLoading()
        view?.showMovies(movies)
    }

    private fun onFailure(throwable: Throwable) {
        view?.hideLoading()
        view?.showError(throwable)
    }

    override fun loadGenreMovies(genre: Genre) {
        val genreId = genre.getIdOnMoviesDatabase()
        pagedMoviesDataSourceFactory = PagedMoviesDataSourceFactory(
            moviesDatabaseRetrofit,
            view,
            genreId,
            ::onFailure
        )

        pagedMoviesDataSourceFactory?.let { factory ->
            addDisposable(factory.moviesPagedListObservable.subscribe(::onSuccess))
        }
    }


    override fun searchMovies(query: String) {
        pagedMoviesDataSourceFactory = PagedMoviesDataSourceFactory(
            moviesDatabaseRetrofit,
            view,
            query,
            ::onFailure
        )

        pagedMoviesDataSourceFactory?.let { factory ->
            addDisposable(factory.moviesPagedListObservable.subscribe(::onSuccess))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        pagedMoviesDataSourceFactory?.onDestroy()
    }
}