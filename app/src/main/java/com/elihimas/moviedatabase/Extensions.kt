package com.elihimas.moviedatabase

const val MIN_QUERY_LEN = 3

fun String?.isValidQuery() = this != null && this.length >= MIN_QUERY_LEN