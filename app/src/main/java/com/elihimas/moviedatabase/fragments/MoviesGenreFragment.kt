package com.elihimas.moviedatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.elihimas.moviedatabase.MoviesDatabaseApplication
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.adapters.MoviesAdapter
import com.elihimas.moviedatabase.contracts.MovesGenreContract
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_genre.*

class MoviesGenreFragment : AbstractView<MovesGenreContract.Presenter>(), MovesGenreContract.MovesGenreView {

    companion object {
        private const val ARG_GENRE = "genre"

        @JvmStatic
        fun newInstance(genre: Genre): MoviesGenreFragment {
            return MoviesGenreFragment().apply {
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
        return inflater.inflate(R.layout.fragment_movie_genre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val genre = arguments?.getSerializable(ARG_GENRE) as Genre
        presenter?.setGenre(genre)

        items_recycler.adapter = moviesAdapter
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

    override fun showError(cause: Throwable) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), R.string.loading_error, Toast.LENGTH_LONG).show()
        }
    }

    override fun showMovies(movies: PagedList<Movie>) {
        requireActivity().runOnUiThread {
            moviesAdapter.submitList(movies)
        }
    }
}