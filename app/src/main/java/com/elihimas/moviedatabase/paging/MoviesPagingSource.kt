package com.elihimas.moviedatabase.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.elihimas.moviedatabase.apis.MoviesDatabaseService
import com.elihimas.moviedatabase.apis.MoviesListResponse
import com.elihimas.moviedatabase.model.Movie
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MoviesPagingSource(
    private val genreId: Int,
    private val service: MoviesDatabaseService
) : RxPagingSource<Long, Movie>() {

    override fun getRefreshKey(state: PagingState<Long, Movie>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Long>): Single<LoadResult<Long, Movie>> {
        val page = params.key ?: 1
        if (page == 0L) {
            return Single.just(
                LoadResult.Page(
                    emptyList(),
                    prevKey = null,
                    nextKey = 1
                )
            )
        }

        return service.listMoviesByGenre(genreId, page)
            .subscribeOn(Schedulers.io())
            .map(::toLoadResult)
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(moviesListResponse: MoviesListResponse): LoadResult<Long, Movie> {
        return LoadResult.Page(
            moviesListResponse.movies,
            null,
            moviesListResponse.page + 1
        )
    }
}