package com.elihimas.moviedatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedList
import com.elihimas.moviedatabase.MoviesDatabaseApplication
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.adapters.MoviesAdapter
import com.elihimas.moviedatabase.contracts.MoviesGenreContract
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie
import kotlinx.android.synthetic.main.fragment_movies_list.*

class MoviesListFragment : AbstractView<MoviesGenreContract.Presenter>(), MoviesGenreContract.MoviesGenreView {

    companion object {
        private const val ARG_GENRE = "genre"

        @JvmStatic
        fun newInstance(genre: Genre): MoviesListFragment {
            return MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_GENRE, genre)
                }
            }
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

        val genre = arguments?.getSerializable(ARG_GENRE) as Genre?
        genre?.let { genre -> presenter?.setGenre(genre) }

        items_recycler.adapter = moviesAdapter
    }

    override fun createPresenter() = MoviesDatabaseApplication.appComponent.moviesGenrePresenter

    override fun showLoading() {
        items_recycler.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        items_recycler.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    override fun showMovies(movies: PagedList<Movie>) {
        moviesAdapter.submitList(movies)
    }

    fun searchMovies(query: String) {
        presenter?.searchMovies(query)
    }
}