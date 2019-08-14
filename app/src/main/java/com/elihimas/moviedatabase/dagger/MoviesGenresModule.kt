package com.elihimas.moviedatabase.dagger

import com.elihimas.moviedatabase.presenters.MoviesGenrePresenter
import dagger.Module
import dagger.Provides

@Module
class MoviesGenresModule {

    @Provides
    fun provideMoviesGenrePresenter() =
        MoviesGenrePresenter()

}