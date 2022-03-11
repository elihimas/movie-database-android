package com.elihimas.moviedatabase.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elihimas.moviedatabase.apis.APIFactory
import com.elihimas.moviedatabase.apis.LoadItemsCallbacks
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.paging.MoviesPagingSource
import com.elihimas.moviedatabase.paging.SearchMoviesPagingSource
import com.elihimas.moviedatabase.viewmodels.states.MoviesListState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.elihimas.moviedatabase.isValidQuery

class MoviesListViewModel : ViewModel(), LoadItemsCallbacks {

    private val currentState = MutableLiveData<MoviesListState>()

    private var searchQuery: String? = null

    private val moviesDatabaseService = APIFactory.createMoviesDatabaseService()

    private val compositeDisposable = CompositeDisposable()

    fun getCurrentState(): LiveData<MoviesListState> = currentState

    fun searchMovies(query: String) {
        searchQuery = query

        if (query.isValidQuery()) {
            currentState.postValue(MoviesListState.LoadingState)

            viewModelScope.launch {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        SearchMoviesPagingSource(
                            query,
                            moviesDatabaseService
                        )
                    }
                ).flow.cachedIn(viewModelScope).collectLatest {
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
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    MoviesPagingSource(
                        genre.getIdOnMoviesDatabase(),
                        moviesDatabaseService
                    )
                }
            ).flow.cachedIn(viewModelScope).collectLatest {
                currentState.postValue(MoviesListState.MoviesLoadedState(it))
            }
        }
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    override fun onNothingFound() {
//        view?.showNothingFound()
    }

    override fun onError(cause: Throwable) {
//        view?.hideLoading()
//        view?.showError(cause)
    }
}
