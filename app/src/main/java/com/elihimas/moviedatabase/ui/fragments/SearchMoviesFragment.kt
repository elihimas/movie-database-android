package com.elihimas.moviedatabase.ui.fragments

class SearchMoviesFragment : AbstractMoviesListFragment() {

    fun searchMovies(query: String) {
        viewModel.searchMovies(query)
    }
}