package com.elihimas.moviedatabase.presenters

import com.elihimas.moviedatabase.api.ListMoviesResponse
import com.elihimas.moviedatabase.contracts.MovesGenreContract
import com.elihimas.moviedatabase.model.Genre
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesGenrePresenter :
    AbstractPresenter<MovesGenreContract.MovesGenreView>(),
    MovesGenreContract.Presenter {

    override fun setGenre(genre: Genre) {
        val api = APIFactory.createMoviesDatabaseRetrofit()

        val onSuccess = fun(response: ListMoviesResponse) {
            view?.showMovies(response)
        }

        val onFailure = fun(throwable: Throwable) {
            view?.showError(throwable)
        }

        api?.let {
            addDisable(
                api.discoverMovies(genre.getDatabaseId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        view?.showLoading()
                    }
                    .doFinally {
                        view?.hideLoading()
                    }
                    .subscribeOn(Schedulers.io())
                    .subscribe(onSuccess, onFailure)

            )
        }


    }

}