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

        itemsRecycler.adapter = moviesAdapter
        genre?.let { genre ->
            presenter?.loadGenreMovies(genre)
        }
    }

    override fun createPresenter() = MoviesDatabaseApplication.appComponent.moviesGenrePresenter

    override fun showLoading() {
        requireActivity().runOnUiThread {
            itemsRecycler.visibility = View.GONE
            progress.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            itemsRecycler.visibility = View.VISIBLE
            progress.visibility = View.GONE
        }
    }

    override fun showMovies(moviesPagedList: PagedList<Movie>) {
        requireActivity().runOnUiThread {
            nothingFoundText.visibility = View.GONE
            moviesAdapter.submitList(moviesPagedList)
        }
    }

    override fun showNothingFound() {
        itemsRecycler.visibility = View.GONE
        progress.visibility = View.GONE
        nothingFoundText.visibility = View.VISIBLE
    }

    fun searchMovies(query: String) {
        presenter?.searchMovies(query)
    }
}