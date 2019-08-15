package com.elihimas.moviedatabase.contracts

import com.elihimas.moviedatabase.fragments.BaseView
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.presenters.BasePresenter

interface MovieDetailsContract {
    interface MovieDetailsView : BaseView {
        fun showMovie(movie: Movie)
    }

    interface Presenter : BasePresenter<MovieDetailsView> {
        fun loadMovie(id: Long)
    }
}