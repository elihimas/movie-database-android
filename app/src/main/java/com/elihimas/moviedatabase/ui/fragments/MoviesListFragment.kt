package com.elihimas.moviedatabase.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import com.elihimas.moviedatabase.model.Genre

class MoviesListFragment() : AbstractMoviesListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genre = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getSerializable("genre", Genre::class.java)
        } else {
            arguments?.getSerializable("genre") as Genre
        }

        genre?.let (viewModel::loadGenreMovies)
    }

    companion object {
        @JvmStatic
        fun newInstance(genre: Genre): MoviesListFragment {
            return MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(genreExtra, genre)
                }
            }
        }

        private const val genreExtra = "genre"
    }
}