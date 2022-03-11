package com.elihimas.moviedatabase.viewmodels.states

import androidx.paging.PagingData
import com.elihimas.moviedatabase.model.Movie

sealed class MoviesListState {
    object NothingFound : MoviesListState()
    data class MoviesLoadedState(val moviesPageData: PagingData<Movie>) : MoviesListState()



    object LoadingState : MoviesListState()
    object WaitingSearchState : MoviesListState()

    data class LoadErrorState(val cause: Throwable) : MoviesListState()
}
