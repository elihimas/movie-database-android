package com.elihimas.moviedatabase.dagger

import com.elihimas.moviedatabase.presenters.MovieDetailsPresenter
import com.elihimas.moviedatabase.presenters.ListMoviesPresenter
import dagger.Module
import dagger.Provides

@Module
class PresentersModule {

    @Provides
    fun provideMoviesGenrePresenter() =
        ListMoviesPresenter()

    @Provides
    fun provideMovieDetailsPresenter() =
        MovieDetailsPresenter()

}