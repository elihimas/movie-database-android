package com.elihimas.moviedatabase.model

import com.elihimas.moviedatabase.R

enum class Genre {
    ACTION, DRAMA, FANTASY, FICTION;

    fun getTitleResId() =
        when (this) {
            ACTION -> R.string.tab_genre_action
            DRAMA -> R.string.tab_genre_drama
            FANTASY -> R.string.tab_genre_fantasy
            FICTION -> R.string.tab_genre_fiction
        }


}