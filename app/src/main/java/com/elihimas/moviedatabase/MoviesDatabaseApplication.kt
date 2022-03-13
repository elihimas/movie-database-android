package com.elihimas.moviedatabase

import android.app.Application
import com.elihimas.moviedatabase.apis.APIFactory
import com.elihimas.moviedatabase.viewmodels.MovieDetailsViewModel
import com.elihimas.moviedatabase.viewmodels.MoviesListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

val moviesDatabaseApplicationAppModules = module {
    single { APIFactory.createMoviesDatabaseService() }

    viewModel { MoviesListViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}

class MoviesDatabaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            //inject Android context
            androidContext(this@MoviesDatabaseApplication)
            // use Android logger - Level.INFO by default
            androidLogger(Level.ERROR)
            // use modules
            modules(moviesDatabaseApplicationAppModules)
        }

    }
}