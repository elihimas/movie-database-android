package com.elihimas.moviedatabase.extensions

import com.elihimas.moviedatabase.R
import com.elihimas.moviedatabase.model.Genres

val Genres.titleResId: Int
    get() = when (this) {
        Genres.ACTION -> R.string.tab_genre_action
        Genres.DRAMA -> R.string.tab_genre_drama
        Genres.FANTASY -> R.string.tab_genre_fantasy
        Genres.FICTION -> R.string.tab_genre_fiction
    }
