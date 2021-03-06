package com.elihimas.moviedatabase.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.elihimas.moviedatabase.MoviesDatabaseApplication
import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.contracts.MovieDetailsContract
import com.elihimas.moviedatabase.fragments.AbstractView
import com.elihimas.moviedatabase.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsActivityFragment : AbstractView<MovieDetailsContract.Presenter>(),
    MovieDetailsContract.View {

    private companion object {
        const val IMAGES_URL = "https://image.tmdb.org/t/p/w500/"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun createPresenter() = MoviesDatabaseApplication.appComponent.movieDetailsPresenter

    override fun showLoading() {
        requireActivity().runOnUiThread {
            content.visibility = View.GONE
            progress.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            progress.visibility = View.GONE
            content.visibility = View.VISIBLE
        }
    }

    override fun showMovie(movie: Movie) {
        description_text.text = movie.overview

        val imageUrl = IMAGES_URL + movie.posterPath
        Glide.with(requireContext())
            .load(imageUrl)
            .centerInside()
            .into(posterImage)
    }

    fun loadMovie(movieId: Long) {
        presenter?.loadMovie(movieId)
    }
}
