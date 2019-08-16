package com.elihimas.moviedatabase

import com.elihimas.moviedatabase.contracts.MovieDetailsContract
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.presenters.MovieDetailsPresenter
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class MovieDetailsPresenterTest : PresentersTest() {

    @Mock
    lateinit var view: MovieDetailsContract.MovieDetailsView

    @Test
    fun loadMovie_success() {
        val throwableMock = mock(Throwable::class.java)
        val presenter = MovieDetailsPresenter()

        presenter.attach(view)

        fun test(movie: Movie) {
            presenter.loadMovie(movie.id)

            verify(view, never()).showError(throwableMock)
            verify(view).showMovie(movie)
        }

        val matrixOverview =
            "Set in the 22nd century, The Matrix tells the story of a computer hacker who joins a group of underground insurgents fighting the vast and powerful computers who now rule the earth."
        val theMatrix = Movie(603, "The Matrix", matrixOverview, "/hEpWvX6Bp79eLxY1kX5ZZJcme5U.jpg")
        test(theMatrix)

        val starWarsOverview =
            "Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire."
        val starWars = Movie(11, "Star Wars", starWarsOverview, "/btTdmkgIvOi0FFip1sPuZI2oQG6.jpg")
        test(starWars)


        val perfectBlueOverview =
            "A retired pop singer turned actress' sense of reality is shaken when she is stalked by an obsessed fan and seemingly a ghost of her past."
        val perfectBlue = Movie(10494, "Perfect Blue", perfectBlueOverview, "/sxBzVuwqIABKIbdij7lOrRvDb15.jpg")
        test(perfectBlue)
    }

    @Test
    fun loadMovie_wrongId() {
        val presenter = MovieDetailsPresenter()
        presenter.attach(view)

        fun test(movieId: Long) {
            presenter.loadMovie(movieId)

            val movieMock = mock(Movie::class.java)
            verify(view, never()).showMovie(movieMock)
        }

        val invalidMoviesIds = listOf(1910392L, 3910392L, 2031992L)
        invalidMoviesIds.forEach { invalidMovieId ->
            test(invalidMovieId)
        }
    }
}
