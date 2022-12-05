package com.miso.vinilos.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.miso.vinilos.R
import org.junit.Rule
import org.junit.Test

class AlbumCreateFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAlbumCreate() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_login_collector)).perform(ViewActions.click())
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withId(R.id.btnAlbumCreate)).perform(ViewActions.click());
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.albumCreateText))
            ?.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}