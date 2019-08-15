package com.elihimas.moviedatabase.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.fragments.MoviesListFragment
import com.elihimas.moviedatabase.model.Genre

import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        private const val ARG_MOVIE_ID = "movieId"

        fun start(context: Context, id: Long) {
            context.startActivity(Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(ARG_MOVIE_ID, id)
            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)

        val movieId = intent.getLongExtra(ARG_MOVIE_ID, -1)
        (fragment as MovieDetailsActivityFragment).loadMovie(movieId)
    }

}
