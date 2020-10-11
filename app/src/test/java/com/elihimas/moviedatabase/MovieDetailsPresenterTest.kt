package com.elihimas.moviedatabase

import com.elihimas.moviedatabase.contracts.MovieDetailsContract
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.presenters.MovieDetailsPresenter
import com.nhaarman.mockitokotlin2.capture
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*


class MovieDetailsPresenterTest : PresentersAbstractTest() {

    @Mock
    lateinit var mockedView: MovieDetailsContract.View

    @Captor
    private lateinit var throwableCaptor: ArgumentCaptor<Throwable>

    @Test
    fun `When load movie by id then should show movie`() {
        val throwableMock = mock(Throwable::class.java)
        val presenter = MovieDetailsPresenter().apply {
            attach(mockedView)
        }

        fun test(movie: Movie) {
            presenter.loadMovie(movie.id)

            verify(mockedView, never()).showError(throwableMock)
            verify(mockedView, atLeastOnce()).showLoading()
            verify(mockedView, atLeastOnce()).showMovie(movie)
            verify(mockedView, atLeastOnce()).hideLoading()
        }

        val matrixOverview =
            "Set in the 22nd century, The Matrix tells the story of a computer hacker who joins a group of underground insurgents fighting the vast and powerful computers who now rule the earth."
        val theMatrix = Movie(603, "The Matrix", matrixOverview, "/dXNAPwY7VrqMAo51EKhhCJfaGb5.jpg")
        test(theMatrix)

        val starWarsOverview =
            "Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire."
        val starWars = Movie(11, "Star Wars", starWarsOverview, "/6FfCtAuVAW8XJjZ7eWeLibRLWTw.jpg")
        test(starWars)

        val perfectBlueOverview =
            "A retired pop singer turned actress' sense of reality is shaken when she is stalked by an obsessed fan and seemingly a ghost of her past."
        val perfectBlue =
            Movie(10494, "Perfect Blue", perfectBlueOverview, "/79vujbsWEbX4dzffBV541QXN6sf.jpg")
        test(perfectBlue)
    }

    @Test
    fun `When load movie with invalid id then should show error`() {
        val presenter = MovieDetailsPresenter().apply {
            attach(mockedView)
        }

        fun test(movieId: Long) {
            presenter.loadMovie(movieId)

            val movieMock = mock(Movie::class.java)
            verify(mockedView, never()).showMovie(movieMock)
            verify(mockedView, atLeastOnce()).hideLoading()
            verify(mockedView, atLeastOnce()).showError(capture(throwableCaptor))
        }

        val invalidMoviesIds = listOf(1910392L, 3910392L, 2031992L)
        invalidMoviesIds.forEach { invalidMovieId ->
            test(invalidMovieId)
        }
    }
}
