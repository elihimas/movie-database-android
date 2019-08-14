package com.elihimas.moviedatabase.contracts

import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.presenters.BasePresenter

interface MovesGenreContract {
    interface MovesGenreView {
        fun showMovies()
    }

    interface Presenter : BasePresenter<MovesGenreView> {
        fun setGenre(genre: Genre)
    }
}