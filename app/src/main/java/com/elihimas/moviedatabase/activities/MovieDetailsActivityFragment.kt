package com.elihimas.moviedatabase.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.elihimas.moviedatabase.MoviesDatabaseApplication
import com.elihimas.moviedatabase.contracts.MovieDetailsContract
import com.elihimas.moviedatabase.databinding.FragmentMovieDetailsBinding
import com.elihimas.moviedatabase.fragments.AbstractView
import com.elihimas.moviedatabase.model.Movie

class MovieDetailsActivityFragment : AbstractView<MovieDetailsContract.Presenter>(),
    MovieDetailsContract.View {

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun createPresenter() = MoviesDatabaseApplication.appComponent.movieDetailsPresenter

    override fun showLoading() {
        requireActivity().runOnUiThread {
            binding.content.visibility = View.GONE
            binding.progress.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            binding.progress.visibility = View.GONE
            binding.content.visibility = View.VISIBLE
        }
    }

    override fun showMovie(movie: Movie) {
        binding.descriptionText.text = movie.overview

        val imageUrl = IMAGES_URL + movie.posterPath
        Glide.with(requireContext())
            .load(imageUrl)
            .centerInside()
            .into(binding.posterImage)
    }

    fun loadMovie(movieId: Long) {
        presenter?.loadMovie(movieId)
    }

    private companion object {
        const val IMAGES_URL = "https://image.tmdb.org/t/p/w500/"
    }
}
