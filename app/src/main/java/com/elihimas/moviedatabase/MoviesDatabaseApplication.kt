package com.elihimas.moviedatabase

import android.app.Application
import android.content.Context
import com.elihimas.moviedatabase.dagger.ApplicationComponent
import com.elihimas.moviedatabase.dagger.DaggerApplicationComponent
import com.elihimas.moviedatabase.dagger.MoviesGenresModule

class MoviesDatabaseApplication : Application() {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder().moviesGenresModule(MoviesGenresModule()).build()
    }
}