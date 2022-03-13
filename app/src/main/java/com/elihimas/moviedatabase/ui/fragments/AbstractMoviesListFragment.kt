package com.elihimas.moviedatabase.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.elihimas.moviedatabase.adapters.MoviesAdapter
import com.elihimas.moviedatabase.databinding.FragmentMoviesListBinding
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.viewmodels.MoviesListViewModel
import com.elihimas.moviedatabase.viewmodels.states.MoviesListState
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class AbstractMoviesListFragment : BaseFragment() {

    private lateinit var binding: FragmentMoviesListBinding
    protected val viewModel by viewModel<MoviesListViewModel>()

    private val moviesAdapter by lazy { MoviesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        binding.itemsRecycler.adapter = moviesAdapter
        moviesAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading
                && moviesAdapter.itemCount == 0
            ) {
                if (viewModel.getCurrentState().value != MoviesListState.WaitingSearchState) {
                    showNothingFound()
                }
            } else if (loadState.source.refresh is LoadState.Loading
                && moviesAdapter.itemCount < 1
            ) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        viewModel.getCurrentState().observe(viewLifecycleOwner, ::updateState)

        return binding.root
    }

    private fun updateState(state: MoviesListState) {
        when (state) {
            MoviesListState.LoadingState -> showLoading()
            MoviesListState.NothingFound -> showNothingFound()
            MoviesListState.WaitingSearchState -> showWaitingSearchState()
            is MoviesListState.LoadErrorState -> showError(state.cause)
            is MoviesListState.MoviesLoadedState -> showMovies(state.moviesPageData)
        }
    }

    private fun showWaitingSearchState() {
        requireActivity().runOnUiThread {
            hideLoading()
            binding.nothingFoundText.visibility = View.GONE
            moviesAdapter.submitData(lifecycle, PagingData.empty())
        }
    }

    private fun showMovies(pagingData: PagingData<Movie>) {
        requireActivity().runOnUiThread {
            binding.nothingFoundText.visibility = View.GONE
            hideLoading()
            moviesAdapter.submitData(lifecycle, pagingData)
        }
    }

    private fun showNothingFound() {
        with(binding) {
            itemsRecycler.visibility = View.GONE
            progress.visibility = View.GONE
            nothingFoundText.visibility = View.VISIBLE
        }
    }


    override fun showLoading() {
        requireActivity().runOnUiThread {
            with(binding) {
                itemsRecycler.visibility = View.GONE
                nothingFoundText.visibility = View.GONE
                progress.visibility = View.VISIBLE
            }
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            with(binding) {
                itemsRecycler.visibility = View.VISIBLE
                progress.visibility = View.GONE
            }
        }
    }

}