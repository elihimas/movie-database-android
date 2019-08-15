package com.elihimas.moviedatabase

import android.app.Application
import com.elihimas.moviedatabase.dagger.ApplicationComponent
import com.elihimas.moviedatabase.dagger.DaggerApplicationComponent
import com.elihimas.moviedatabase.dagger.PresentersModule

class MoviesDatabaseApplication : Application() {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent =
            DaggerApplicationComponent
                .builder()
                .presentersModule(PresentersModule())
                .build()
    }
}