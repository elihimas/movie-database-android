package com.elihimas.moviedatabase.presenters

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.elihimas.moviedatabase.contracts.MovesGenreContract
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie

class MoviesGenrePresenter :
    AbstractPresenter<MovesGenreContract.MovesGenreView>(),
    MovesGenreContract.Presenter {

    private lateinit var pagedMoviesDataSourceFactory: PagedMoviesDataSourceFactory


    override fun setGenre(genre: Genre) {
        val moviesDatabaseRetrofit = APIFactory.createMoviesDatabaseRetrofit()

        view?.showLoading()

        val onSuccess = fun(movies: PagedList<Movie>) {
            view?.hideLoading()
            view?.showMovies(movies)
        }

        val onFailure = fun(throwable: Throwable) {
            view?.hideLoading()
            view?.showError(throwable)
        }

        pagedMoviesDataSourceFactory = PagedMoviesDataSourceFactory(
            moviesDatabaseRetrofit,
            genre.getIdOnMoviesDatabase(),
            onSuccess,
            onFailure
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        pagedMoviesDataSourceFactory.onDestroy()
    }
}

