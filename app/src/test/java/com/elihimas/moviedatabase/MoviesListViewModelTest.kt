package com.elihimas.moviedatabase

import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.elihimas.moviedatabase.model.Genre
import com.elihimas.moviedatabase.model.Movie
import com.elihimas.moviedatabase.repository.MoviesRepository
import com.elihimas.moviedatabase.viewmodels.MoviesListViewModel
import com.elihimas.moviedatabase.viewmodels.states.MoviesListState
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainDispatcherRule(private val dispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)

    override fun finished(description: Description?) = Dispatchers.resetMain()

}

class MoviesListViewModelTest : BaseViewModelTest() {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = mockk<MoviesRepository>()
    private val viewModel = MoviesListViewModel(repository)

    private val actionMovies = listOf(
        Movie(10, "First Blood", "First Blood overview", "/firstBloodPoster"),
        Movie(12, "Die Hard", "Die Hard overview", "/dieHardPoster"),
        Movie(45, "The Terminator", "The Terminator overview", "/terminatorPoster"),
    )

    private val actionPagingData = PagingData.from(actionMovies)
    private val emptyPagingData = PagingData.empty<Movie>()

    private val nothingFoundQuery = "nothing found query"

    @Before
    fun setup() {
        every {
            repository.loadGenreMovies(Genre.ACTION)
        } returns
                flow {
                    emit(actionPagingData)
                }

        every { repository.searchMovies(nothingFoundQuery) } returns flow { emit(emptyPagingData) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when load by genre should load the movies of the genre`() = runTest {
        val observer = mockk<Observer<MoviesListState>> {
            every { onChanged(any()) } just runs
        }
        viewModel.getCurrentState().observeForever(observer)

        viewModel.loadGenreMovies(Genre.ACTION)

        verifySequence {
            observer.onChanged(MoviesListState.LoadingState)
            observer.onChanged(MoviesListState.MoviesLoadedState(actionPagingData))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when searching for a query with no result should change to nothing found`() = runTest {
        val observer = mockk<Observer<MoviesListState>> {
            every { onChanged(any()) } just runs
        }
        viewModel.getCurrentState().observeForever(observer)

        viewModel.searchMovies(nothingFoundQuery)

        verifySequence {
            observer.onChanged(MoviesListState.LoadingState)
            observer.onChanged(MoviesListState.NothingFound)
        }
    }
}