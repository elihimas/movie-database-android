package com.elihimas.moviedatabase.dagger

import com.elihimas.moviedatabase.presenters.MovieDetailsPresenter
import com.elihimas.moviedatabase.presenters.MoviesGenrePresenter
import dagger.Module
import dagger.Provides

@Module
class PresentersModule {

    @Provides
    fun provideMoviesGenrePresenter() =
        MoviesGenrePresenter()

    @Provides
    fun provideMovieDetailsPresenter() =
        MovieDetailsPresenter()

}