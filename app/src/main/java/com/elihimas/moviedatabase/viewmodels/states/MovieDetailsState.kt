package com.elihimas.moviedatabase.viewmodels.states

import com.elihimas.moviedatabase.model.Movie

sealed class MovieDetailsState {
    object LoadingState : MovieDetailsState()
    data class LoadErrorState(val cause: Throwable) : MovieDetailsState()
    data class MovieLoadedState(val movie: Movie) : MovieDetailsState()
}
