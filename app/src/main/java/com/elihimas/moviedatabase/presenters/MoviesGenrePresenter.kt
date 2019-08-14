package com.elihimas.moviedatabase.presenters

import com.elihimas.moviedatabase.contracts.MovesGenreContract
import com.elihimas.moviedatabase.fragments.MoviesGenreFragment
import com.elihimas.moviedatabase.model.Genre

class MoviesGenrePresenter :
    AbstractPresenter<MovesGenreContract.MovesGenreView>(),
    MovesGenreContract.Presenter {

    override fun setGenre(genre: Genre) {
        view?.showMovies()
    }

}