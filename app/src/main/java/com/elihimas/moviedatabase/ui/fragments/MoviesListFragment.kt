package com.elihimas.moviedatabase.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.elihimas.moviedatabase.adapters.MoviesAdapter
import com.elihimas.moviedatabase.databinding.FragmentMoviesListBinding
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.viewmodels.MoviesListViewModel
import com.elihimas.moviedatabase.viewmodels.states.MoviesListState
import kotlinx.coroutines.launch

class MoviesListFragment(val genre: Genre) : AbstractMoviesListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadGenreMovies(genre)
    }

    companion object {
        @JvmStatic
        fun newInstance(genre: Genre): MoviesListFragment {
            return MoviesListFragment(genre)
        }
    }
}