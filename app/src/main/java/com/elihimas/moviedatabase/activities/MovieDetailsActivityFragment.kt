package com.elihimas.moviedatabase.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.elihimas.moviedatabase.databinding.FragmentMovieDetailsBinding
import com.elihimas.moviedatabase.fragments.BaseFragment
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.viewmodels.MovieDetailsViewModel
import com.elihimas.moviedatabase.viewmodels.states.MovieDetailsState

class MovieDetailsActivityFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel by activityViewModels<MovieDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentState().observe(viewLifecycleOwner, ::render)
    }

    private fun render(movieDetailsState: MovieDetailsState) {
        when (movieDetailsState) {
            MovieDetailsState.LoadingState -> showLoading()
            is MovieDetailsState.LoadErrorState -> showError(movieDetailsState.cause)
            is MovieDetailsState.MovieLoadedState -> showMovie(movieDetailsState.movie)
        }
    }

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

    private fun showMovie(movie: Movie) {
        hideLoading()
        binding.descriptionText.text = movie.overview

        val imageUrl = IMAGES_URL + movie.posterPath
        Glide.with(requireContext())
            .load(imageUrl)
            .centerInside()
            .into(binding.posterImage)
    }

    private companion object {
        const val IMAGES_URL = "https://image.tmdb.org/t/p/w500/"
    }
}
