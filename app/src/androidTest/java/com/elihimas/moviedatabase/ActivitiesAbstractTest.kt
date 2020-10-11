package com.elihimas.moviedatabase

import android.app.Activity
import android.app.Instrumentation
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import com.elihimas.moviedatabase.activities.MoviesGenresActivity
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class ActivitiesAbstractTest {

    @Rule
    @JvmField
    val moviesGenresActivityRule: IntentsTestRule<MoviesGenresActivity> =
        IntentsTestRule(MoviesGenresActivity::class.java)

    @Before
    fun setup() {
        Intents.intending(CoreMatchers.not(IntentMatchers.isInternal()))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    fun isTextVisible(text: String) {
        onView(withText(text)).check(matches(isDisplayed()))
    }

    fun goToSearch() {
        onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
    }

    fun typeSearchQuery(query: String) {
        onView(ViewMatchers.isAssignableFrom(EditText::class.java)).perform(ViewActions.typeText(query))
        onView(ViewMatchers.isAssignableFrom(EditText::class.java)).perform(ViewActions.closeSoftKeyboard())
        Thread.sleep(5000)
    }
}
