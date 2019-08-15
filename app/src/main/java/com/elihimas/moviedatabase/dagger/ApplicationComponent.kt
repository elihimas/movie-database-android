package com.elihimas.moviedatabase.dagger

import com.elihimas.moviedatabase.presenters.MovieDetailsPresenter
import com.elihimas.moviedatabase.presenters.MoviesGenrePresenter
import dagger.Component

@Component(modules = [PresentersModule::class])
interface ApplicationComponent {

    val moviesGenrePresenter: MoviesGenrePresenter

    val movieDetailsPresenter: MovieDetailsPresenter

}