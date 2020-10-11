package com.elihimas.moviedatabase

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.not
import org.junit.Test

class SearchMovieTest : ActivitiesAbstractTest() {

    @Test
    fun searchMovie_nothingFound() {
        val wrongMatrixName = "matrixx"

        goToSearch()
        typeSearchQuery(wrongMatrixName)
        onView(withId(R.id.nothingFoundText)).check(matches(isDisplayed()))
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))
    }

    @Test
    fun searchMovie_success() {
        val partialMatrixName = "matri"

        goToSearch()
        typeSearchQuery(partialMatrixName)
        isTextVisible("The Matrix")
    }
}
