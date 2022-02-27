package com.elihimas.moviedatabase.dagger

import com.elihimas.moviedatabase.presenters.ListMoviesPresenter
import dagger.Component

@Component(modules = [PresentersModule::class])
interface ApplicationComponent {

    val moviesGenrePresenter: ListMoviesPresenter

}