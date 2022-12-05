package com.miso.vinilos.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.miso.vinilos.R
import org.junit.Rule
import org.junit.Test

class CollectorDetailFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testListAlbumDetails() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_login_collector)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.collectorsFragment)).perform(ViewActions.click())
        Thread.sleep(5000)
        Espresso.onView(withIndex(ViewMatchers.withId(R.id.cly), 0)).perform(ViewActions.click());
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withId(R.id.linearLayout))
            ?.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}