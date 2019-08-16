package com.elihimas.moviedatabase.contracts

import androidx.paging.PagedList
import com.elihimas.moviedatabase.fragments.BaseView
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.presenters.BasePresenter

interface MoviesListContract {
    interface View : BaseView {
        fun showMovies(movies: PagedList<Movie>)
        fun showNothingFound()
    }

    interface Presenter : BasePresenter<View> {
        fun loadGenreMovies(genre: Genre)
        fun searchMovies(query: String)
    }
}