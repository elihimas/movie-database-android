package com.elihimas.moviedatabase

import androidx.lifecycle.Observer
import com.elihimas.moviedatabase.apis.MoviesDatabaseService
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.viewmodels.MovieDetailsViewModel
import com.elihimas.moviedatabase.viewmodels.states.MovieDetailsState
import io.mockk.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MovieDetailsViewModelTest : BaseViewModelTest() {

    private val service = mockk<MoviesDatabaseService>()
    private val viewModel = MovieDetailsViewModel(service)

    private val matrix = Movie(10, "The Matrix", "The matrix overview", "/imagePath")
    private val invalidMovieId = 1515L

    private val exception = mockk<Throwable>()

    @Before
    fun setup() {
        every { service.getMovieDetails(matrix.id) } returns Single.just(matrix)
        every { service.getMovieDetails(invalidMovieId) } returns Single.error(exception)
    }

    @Test
    fun `when load movie with valid id then should load the movie`() {
        val observer = mockk<Observer<MovieDetailsState>> {
            every { onChanged(any()) } just runs
        }
        viewModel.currentState().observeForever(observer)

        viewModel.loadMovie(matrix.id)

        verifySequence {
            observer.onChanged(MovieDetailsState.LoadingState)
            observer.onChanged(MovieDetailsState.MovieLoadedState(matrix))
        }
    }

    @Test
    fun `when load movie with invalid id then should result in error`() {
        val observer = mockk<Observer<MovieDetailsState>> {
            every { onChanged(any()) } just runs
        }
        viewModel.currentState().observeForever(observer)

        viewModel.loadMovie(invalidMovieId)

        verifySequence {
            observer.onChanged(MovieDetailsState.LoadingState)
            observer.onChanged(MovieDetailsState.LoadErrorState(exception))
        }
    }
}