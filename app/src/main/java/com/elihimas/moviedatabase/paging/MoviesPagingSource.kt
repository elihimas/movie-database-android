package com.elihimas.moviedatabase.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elihimas.moviedatabase.apis.MoviesDatabaseService
import com.elihimas.moviedatabase.model.Movie

class MoviesPagingSource(
    private val genreId: Int,
    private val service: MoviesDatabaseService
) :
    PagingSource<Long, Movie>() {

    override fun getRefreshKey(state: PagingState<Long, Movie>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Movie> {
        val page = params.key ?: 1
        if (page == 0L) {
            return LoadResult.Page(
                emptyList(),
                null,
                1
            )
        }

        val moviesResponse = service.listMoviesByGenre(genreId, page)
        val movies = moviesResponse.movies
        val previousPage: Long? = if (page > 0) {
            page - 1
        } else {
            null
        }
        val nextKey: Long = moviesResponse.page + 1

        return LoadResult.Page(
            movies,
            previousPage,
            nextKey
        )
    }

}
