package com.miso.vinilos.ui

import org.junit.Assert.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest
import com.miso.vinilos.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testListAlbums() {
        onView(withId(R.id.btn_login_collector)).perform(click())
        onView(withId(R.id.albumsFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.albums_text)).check(matches(isDisplayed()))
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
    }
}