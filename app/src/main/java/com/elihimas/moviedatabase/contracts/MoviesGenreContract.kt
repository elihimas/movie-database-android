package com.elihimas.moviedatabase.contracts

import androidx.paging.PagedList
import com.elihimas.moviedatabase.fragments.BaseView
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.presenters.BasePresenter

interface MoviesGenreContract {
    interface MoviesGenreView : BaseView {
        fun showMovies(movies: PagedList<Movie>)
    }

    interface Presenter : BasePresenter<MoviesGenreView> {
        fun setGenre(genre: Genre)
        fun searchMovies(query: String)
    }
}