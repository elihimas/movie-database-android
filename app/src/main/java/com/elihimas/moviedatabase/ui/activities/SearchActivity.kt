package com.elihimas.moviedatabase.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.databinding.ActivitySearchBinding
import com.elihimas.moviedatabase.ui.fragments.SearchMoviesFragment
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setTitle(R.string.search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun performSearch(query: String) {
        val searchFragment =
            supportFragmentManager.findFragmentById(R.id.searchFragment) as SearchMoviesFragment
        searchFragment.searchMovies(query)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setQuery("", false)
            requestFocus()

            queryHint = resources.getString(R.string.search_hint)

            isIconified = false

            forceRemoveAllBackgrounds(this)

            RxSearchView.queryTextChanges(this)
                .debounce(DEBOUNCE_MILLISECONDS, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe { textEvent ->
                    performSearch(textEvent.toString())
                }
        }

        return true
    }

    private fun forceRemoveAllBackgrounds(viewGroup: ViewGroup) {
        viewGroup.children.forEach { child ->
            child.background = null

            if (child is ViewGroup) {
                forceRemoveAllBackgrounds(child)
            }
        }
    }

    companion object {
        private const val DEBOUNCE_MILLISECONDS = 500L

        fun start(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }

}
