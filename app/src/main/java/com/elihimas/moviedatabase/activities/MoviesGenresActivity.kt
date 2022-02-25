package com.elihimas.moviedatabase.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.adapters.MoviesGenresPagerAdapter
import com.elihimas.moviedatabase.databinding.ActivityMoviesGenresBinding

class MoviesGenresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesGenresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        with(binding) {
            moviesGenresPager.adapter =
                MoviesGenresPagerAdapter(this@MoviesGenresActivity, supportFragmentManager)
            tabsGenres.setupWithViewPager(moviesGenresPager)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movies_genres_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_search -> {
                startSearchActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    private fun startSearchActivity() {
        SearchActivity.start(this)
    }

}