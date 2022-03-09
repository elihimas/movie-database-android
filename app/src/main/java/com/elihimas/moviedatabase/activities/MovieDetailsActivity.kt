package com.elihimas.moviedatabase.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.apis.APIFactory
import com.elihimas.moviedatabase.databinding.ActivityMovieDetailsBinding
import com.elihimas.moviedatabase.viewmodels.MovieDetailsViewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel by viewModels<MovieDetailsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieDetailsViewModel(APIFactory.createMoviesDatabaseService()) as T
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getLongExtra(ARG_MOVIE_ID, -1)
        val movieTitle = intent.getStringExtra(ARG_MOVIE_TITLE)

        supportActionBar?.title = movieTitle

        viewModel.loadMovie(movieId)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

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
}
