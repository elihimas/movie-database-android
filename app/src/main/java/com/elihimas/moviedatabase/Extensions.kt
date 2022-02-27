package com.elihimas.moviedatabase

import com.elihimas.moviedatabase.apis.MoviesDataSource

fun String?.isValidQuery() = this != null && this.length >= MoviesDataSource.MIN_QUERY_LEN