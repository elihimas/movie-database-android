package com.elihimas.moviedatabase.viewmodels.states

import androidx.paging.PagingData
import com.elihimas.moviedatabase.model.Movie

sealed class MoviesListState {
    object NothingFound : MoviesListState()
    class MoviesLoadedState(val moviesPagingData: PagingData<Movie>) : MoviesListState() {
        override fun equals(other: Any?): Boolean {
            if (other is MoviesLoadedState) {
                return moviesPagingData == other.moviesPagingData
            }
            return super.equals(other)
        }
    }


    object LoadingState : MoviesListState()
    object WaitingSearchState : MoviesListState()

    data class LoadErrorState(val cause: Throwable) : MoviesListState()
}
