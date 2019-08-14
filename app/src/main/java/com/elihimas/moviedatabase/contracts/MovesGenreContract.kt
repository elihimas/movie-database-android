package com.elihimas.moviedatabase.contracts

import com.elihimas.moviedatabase.api.ListMoviesResponse
import com.elihimas.moviedatabase.fragments.BaseView
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.presenters.BasePresenter

interface MovesGenreContract {
    interface MovesGenreView : BaseView {
        fun showMovies(response: ListMoviesResponse)
    }

    interface Presenter : BasePresenter<MovesGenreView> {
        fun setGenre(genre: Genre)
    }
}