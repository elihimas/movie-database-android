package com.elihimas.moviedatabase.presenters

import com.elihimas.moviedatabase.apis.APIFactory
import com.elihimas.moviedatabase.contracts.MovieDetailsContract
import com.elihimas.moviedatabase.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsPresenter :
    AbstractPresenter<MovieDetailsContract.View>(),
    MovieDetailsContract.Presenter {

    private val moviesDatabaseRetrofit = APIFactory.createMoviesDatabaseRetrofit()

    override fun loadMovie(id: Long) {
        addDisposable(
            moviesDatabaseRetrofit.getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view?.showLoading()
                }
                .doFinally {
                    view?.hideLoading()
                }
                .subscribe(::onLoadMovieSuccess, ::onLoadMovieError)
        )
    }

    private fun onLoadMovieSuccess(movie: Movie) {
        view?.showMovie(movie)
    }

    private fun onLoadMovieError(cause: Throwable) {
        view?.showError(cause)
    }
}
