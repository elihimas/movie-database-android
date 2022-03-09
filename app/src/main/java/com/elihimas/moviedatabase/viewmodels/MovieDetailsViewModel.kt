package com.elihimas.moviedatabase.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elihimas.moviedatabase.apis.APIFactory
import com.elihimas.moviedatabase.apis.MoviesDatabaseService
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.viewmodels.states.MovieDetailsState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val moviesDatabaseService: MoviesDatabaseService) : ViewModel() {

    private val currentState = MutableLiveData<MovieDetailsState>(MovieDetailsState.LoadingState)

    private val compositeDisposable = CompositeDisposable()

    fun currentState(): LiveData<MovieDetailsState> = currentState

    fun loadMovie(movieId: Long) {
        addDisposable(
            moviesDatabaseService.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onLoadMovieSuccess, ::onLoadMovieError)
        )
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun onLoadMovieSuccess(movie: Movie) {
        currentState.value = MovieDetailsState.MovieLoadedState(movie)
    }

    private fun onLoadMovieError(cause: Throwable) {
        currentState.value = MovieDetailsState.LoadErrorState(cause)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}