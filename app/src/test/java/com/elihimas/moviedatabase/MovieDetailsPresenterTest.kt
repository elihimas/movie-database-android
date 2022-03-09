package com.elihimas.moviedatabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elihimas.moviedatabase.apis.MoviesDatabaseService
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.viewmodels.MovieDetailsViewModel
import com.elihimas.moviedatabase.viewmodels.states.MovieDetailsState
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class MovieDetailsPresenterTest : PresentersAbstractTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val service = mock(MoviesDatabaseService::class.java)
    private val viewModel = MovieDetailsViewModel(service)


    @Before
    fun setUp() {
        viewModel.currentState().observeForever {}
    }

    @Test
    fun `When load movie by id then should show movie`() {

        fun test(movie: Movie) {
            viewModel.loadMovie(movie.id)

            assertTrue(viewModel.currentState().value is MovieDetailsState.MovieLoadedState)
            val loadedMovie =
                (viewModel.currentState().value as MovieDetailsState.MovieLoadedState).movie
            assertEquals(movie, loadedMovie)

//            verify(mockedView, never()).showError(throwableMock)
//            verify(mockedView, atLeastOnce()).showLoading()
//            verify(mockedView, atLeastOnce()).showMovie(movie)
//            verify(mockedView, atLeastOnce()).hideLoading()
        }

        val matrixOverview =
            "Set in the 22nd century, The Matrix tells the story of a computer hacker who joins a group of underground insurgents fighting the vast and powerful computers who now rule the earth."
        val theMatrix = Movie(603, "The Matrix", matrixOverview, "/dXNAPwY7VrqMAo51EKhhCJfaGb5.jpg")
        `when`(service.getMovieDetails(603)).thenReturn(Single.just(theMatrix))
        test(theMatrix)

        val starWarsOverview =
            "Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire."
        val starWars = Movie(11, "Star Wars", starWarsOverview, "/6FfCtAuVAW8XJjZ7eWeLibRLWTw.jpg")
        `when`(service.getMovieDetails(11)).thenReturn(Single.just(starWars))
        test(starWars)

        val perfectBlueOverview =
            "A retired pop singer turned actress' sense of reality is shaken when she is stalked by an obsessed fan and seemingly a ghost of her past."
        val perfectBlue =
            Movie(10494, "Perfect Blue", perfectBlueOverview, "/79vujbsWEbX4dzffBV541QXN6sf.jpg")
        `when`(service.getMovieDetails(10494)).thenReturn(Single.just(perfectBlue))
        test(perfectBlue)
    }

    @Test
    fun `When load movie with invalid id then should show error`() {
        fun test(movieId: Long) {
            viewModel.loadMovie(movieId)

            assertTrue(viewModel.currentState().value is MovieDetailsState.LoadErrorState)
//            val loadedMovie =
//                (viewModel.currentState().value as MovieDetailsState.MovieLoadedState).movie
//            assertEquals(movie, loadedMovie)

            val movieMock = mock(Movie::class.java)
//            verify(mockedView, never()).showMovie(movieMock)
//            verify(mockedView, atLeastOnce()).hideLoading()
//            verify(mockedView, atLeastOnce()).showError(capture(throwableCaptor))

        }

        val invalidMoviesIds = listOf(1910392L, 3910392L, 2031992L)
        invalidMoviesIds.forEach {
            `when`(service.getMovieDetails(it)).thenReturn(Single.error(IOException()))
        }
        invalidMoviesIds.forEach { invalidMovieId ->
            test(invalidMovieId)
        }
    }
}
