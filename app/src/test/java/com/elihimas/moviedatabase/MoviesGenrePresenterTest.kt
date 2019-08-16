package com.elihimas.moviedatabase

import com.elihimas.moviedatabase.contracts.ListMoviesContract
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.presenters.ListMoviesPresenter
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class MoviesGenrePresenterTest : PresentersTest() {

    @Mock
    lateinit var view: ListMoviesContract.MoviesGenreView

    @Test
    fun searchMovies_success() {
        val throwableMock = mock(Throwable::class.java)
        val presenter = ListMoviesPresenter()

        presenter.attach(view)

        fun test(query: String) {
            presenter.searchMovies(query)

            verify(view, never()).showError(throwableMock)
        }

        test("Matrix")
        test("star Wars")
        test("perfect Blue")
    }

    @Test
    fun searchMovies_nothingFound() {
        val throwableMock = mock(Throwable::class.java)
        val presenter = ListMoviesPresenter()

        presenter.attach(view)

        fun test(query: String) {
            presenter.searchMovies(query)

            verify(view, never()).showError(throwableMock)
        }

        test("tMatrix")
        test("starWars")
        test("perfectBlue")
    }

    @Test
    fun setGenre_wrongId() {
        val throwableMock = mock(Throwable::class.java)
        val presenter = ListMoviesPresenter()
        presenter.attach(view)

        fun test(genre: Genre) {
            presenter.loadGenreMovies(genre)

            verify(view, never()).showError(throwableMock)
        }

        Genre.values().forEach { genre ->
            test(genre)
        }
    }
}
