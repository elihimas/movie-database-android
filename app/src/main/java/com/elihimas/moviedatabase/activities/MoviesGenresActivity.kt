package com.elihimas.moviedatabase.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.adapters.MoviesGenresPagerAdapter
import kotlinx.android.synthetic.main.activity_movies_genres.*

class MoviesGenresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_genres)
        setSupportActionBar(toolbar)

        movies_genres_pager.adapter = MoviesGenresPagerAdapter(this, supportFragmentManager)
        tabsGenres.setupWithViewPager(movies_genres_pager)

        //TODO remove
        startSearchActivity()
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