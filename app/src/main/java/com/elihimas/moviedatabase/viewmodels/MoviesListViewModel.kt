package com.elihimas.moviedatabase.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.elihimas.moviedatabase.isValidQuery
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.repository.MoviesRepository
import com.elihimas.moviedatabase.viewmodels.states.MoviesListState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoviesListViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val currentState = MutableLiveData<MoviesListState>()

    private var searchQuery: String? = null

    private val compositeDisposable = CompositeDisposable()

    fun getCurrentState(): LiveData<MoviesListState> = currentState

    fun searchMovies(query: String) {
        searchQuery = query

        if (query.isValidQuery()) {
            currentState.postValue(MoviesListState.LoadingState)

            viewModelScope.launch {
                repository.searchMovies(query).cachedIn(viewModelScope).collectLatest {
                    currentState.postValue(MoviesListState.MoviesLoadedState(it))
                }
            }
        } else {
            currentState.postValue(MoviesListState.WaitingSearchState)
        }
    }

    fun loadGenreMovies(genre: Genre) {
        currentState.postValue(MoviesListState.LoadingState)

        viewModelScope.launch {
            repository.loadGenreMovies(genre).cachedIn(viewModelScope).collectLatest {
                currentState.postValue(MoviesListState.MoviesLoadedState(it))
            }
        }
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}
