package com.elihimas.moviedatabase.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.fragments.MoviesGenresPagerAdapter
import kotlinx.android.synthetic.main.activity_movies_genres.*

class MoviesGenresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_genres)

        val sectionsPagerAdapter =
            MoviesGenresPagerAdapter(this, supportFragmentManager)
        vpMoviesGenres.adapter = sectionsPagerAdapter
        tabsGenres.setupWithViewPager(vpMoviesGenres)
    }
}