package com.elihimas.moviedatabase

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchMovieTest : ActivitiesAbstractTest() {

    @Test
    fun searchMovie_nothingFound() {
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.elihimas.moviedatabase", appContext.packageName)

        val wrongMatrixName = "matrixx"

        goToSearch()
        typeSearchQuery(wrongMatrixName)
        onView(withId(R.id.nothing_found_text)).check(matches(isDisplayed()))
    }

    @Test
    fun searchMovie_success() {
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.elihimas.moviedatabase", appContext.packageName)

        val partialMatrixName = "matri"

        goToSearch()
        typeSearchQuery(partialMatrixName)
        isTextVisible("Matrix")
    }
}
