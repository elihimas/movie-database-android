package com.elihimas.moviedatabase

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test

class MoviesGenresTest : ActivitiesAbstractTest() {

    @Test
    fun swipeTabs() {
        val genres = listOf("AÇÃO","DRAMA","FANTASIA","FICÇÃO")

        genres.forEach {
            isTextVisible(it)
        }

        onView(withId(R.id.moviesGenresPager))
            .perform(swipeUp())
            .perform(swipeUp())
            .perform(swipeUp())
            .perform(swipeUp())
            .perform(swipeLeft())
            .perform(swipeLeft())
            .perform(swipeLeft())
            .perform(swipeRight())
            .perform(swipeLeft())
            .perform(swipeLeft())
            .perform(swipeDown())
            .perform(swipeUp())
            .perform(swipeRight())
            .perform(swipeRight())
            .perform(swipeRight())
            .perform(swipeUp())
            .perform(swipeRight())
            .perform(swipeUp())
    }

}
