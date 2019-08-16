package com.elihimas.moviedatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import com.elihimas.moviedatabase.MoviesDatabaseApplication
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.adapters.MoviesAdapter
import com.elihimas.moviedatabase.contracts.MoviesListContract
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie
import kotlinx.android.synthetic.main.fragment_movies_list.*

class MoviesListFragment(val genre: Genre? = null) : AbstractView<MoviesListContract.Presenter>(),
    MoviesListContract.View {

    companion object {
        @JvmStatic
        fun newInstance(genre: Genre): MoviesListFragment {
            return MoviesListFragment(genre)
        }
    }

    private val moviesAdapter by lazy {
        MoviesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        items_recycler.adapter = moviesAdapter
        genre?.let { genre -> presenter?.loadGenreMovies(genre) }
    }

    override fun createPresenter() = MoviesDatabaseApplication.appComponent.moviesGenrePresenter

    override fun showLoading() {
        requireActivity().runOnUiThread {
            items_recycler.visibility = View.GONE
            progress_bar.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            items_recycler.visibility = View.VISIBLE
            progress_bar.visibility = View.GONE
        }
    }

    override fun showMovies(pagedMovies: PagedList<Movie>) {
        requireActivity().runOnUiThread {
            nothing_found_text.visibility = View.GONE
            moviesAdapter.submitList(pagedMovies)
        }
    }

    override fun showNothingFound() {
        items_recycler.visibility = View.GONE
        progress_bar.visibility = View.GONE
        nothing_found_text.visibility = View.VISIBLE
    }

    fun searchMovies(query: String) {
        presenter?.searchMovies(query)
    }
}