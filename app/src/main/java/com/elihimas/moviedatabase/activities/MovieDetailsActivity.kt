package com.elihimas.moviedatabase.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.elihimas.moviedatabase.R

import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        private const val ARG_MOVIE_ID = "movieId"
        private const val ARG_MOVIE_TITLE = "movieTitle"

        fun start(context: Context, movieId: Long, movieTitle: String) {
            context.startActivity(Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(ARG_MOVIE_ID, movieId)
                putExtra(ARG_MOVIE_TITLE, movieTitle)
            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getLongExtra(ARG_MOVIE_ID, -1)
        val movieTitle = intent.getStringExtra(ARG_MOVIE_TITLE)

        supportActionBar?.title = movieTitle

        (fragment as MovieDetailsActivityFragment).loadMovie(movieId)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
