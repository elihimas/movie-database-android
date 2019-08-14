package com.elihimas.moviedatabase.dagger

import com.elihimas.moviedatabase.fragments.MoviesGenreFragment
import com.elihimas.moviedatabase.presenters.MoviesGenrePresenter
import dagger.Component

@Component(modules = [MoviesGenresModule::class])
interface ApplicationComponent {

    val moviesGenrePresenter: MoviesGenrePresenter

    fun inject(moviesGenreFragment: MoviesGenreFragment)

}