package com.elihimas.moviedatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elihimas.moviedatabase.MoviesDatabaseApplication
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.api.ListMoviesResponse
import com.elihimas.moviedatabase.contracts.MovesGenreContract
import com.elihimas.moviedatabase.model.Genre
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
    }

    override fun createPresenter() = MoviesDatabaseApplication.appComponent.moviesGenrePresenter

    override fun showLoading() {
        requireActivity().runOnUiThread {
            tvLabel.text = "${tvLabel.text} \nshowLoading()"
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            tvLabel.text = "${tvLabel.text} \nhideLoading()"
        }
    }

    override fun showError(cause: Throwable) {
        requireActivity().runOnUiThread {
            tvLabel.text = "${tvLabel.text} \nshowError() ${cause.localizedMessage}"
        }
    }

    override fun showMovies(response: ListMoviesResponse) {
        requireActivity().runOnUiThread {
            tvLabel.text = "${tvLabel.text} \nshowMovies() page: ${response.page}"
        }
    }

}