package com.elihimas.moviedatabase.presenters

import androidx.paging.PagedList
import com.elihimas.moviedatabase.contracts.MovesGenreContract
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie

class MoviesGenrePresenter :
    AbstractPresenter<MovesGenreContract.MovesGenreView>(),
    MovesGenreContract.Presenter {

    private var pagedMoviesDataSourceFactory: PagedMoviesDataSourceFactory? = null

    private fun onSuccess(movies: PagedList<Movie>) {
        view?.hideLoading()
        view?.showMovies(movies)
    }

    private fun onFailure(throwable: Throwable) {
        view?.hideLoading()
        view?.showError(throwable)
    }

    override fun searchMovies(query: String) {
        val moviesDatabaseRetrofit = APIFactory.createMoviesDatabaseRetrofit()

        view?.showLoading()

        pagedMoviesDataSourceFactory = PagedMoviesDataSourceFactory(
            moviesDatabaseRetrofit,
            ::onSuccess,
            ::onFailure,
            query
        )
    }


    override fun setGenre(genre: Genre) {
        val moviesDatabaseRetrofit = APIFactory.createMoviesDatabaseRetrofit()

        view?.showLoading()

        val genreId = genre.getIdOnMoviesDatabase()
        pagedMoviesDataSourceFactory = PagedMoviesDataSourceFactory(
            moviesDatabaseRetrofit,
            ::onSuccess,
            ::onFailure,
            genreId
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        pagedMoviesDataSourceFactory?.onDestroy()
    }
}