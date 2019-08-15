package com.elihimas.moviedatabase.presenters

import androidx.paging.PagedList
import com.elihimas.moviedatabase.contracts.MoviesGenreContract
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie

class MoviesGenrePresenter :
    AbstractPresenter<MoviesGenreContract.MoviesGenreView>(),
    MoviesGenreContract.Presenter {

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

    override fun setGenre(genre: Genre) {
        val genreId = genre.getIdOnMoviesDatabase()
        pagedMoviesDataSourceFactory = PagedMoviesDataSourceFactory(
            moviesDatabaseRetrofit,
            view,
            genreId,
            ::onSuccess,
            ::onFailure
        )
    }


    override fun searchMovies(query: String) {
        pagedMoviesDataSourceFactory = PagedMoviesDataSourceFactory(
            moviesDatabaseRetrofit,
            view,
            query,
            ::onSuccess,
            ::onFailure
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        pagedMoviesDataSourceFactory?.onDestroy()
    }
}