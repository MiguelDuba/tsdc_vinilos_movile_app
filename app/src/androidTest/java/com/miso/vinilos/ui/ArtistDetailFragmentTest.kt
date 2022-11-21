package com.miso.vinilos.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import org.junit.Assert.*
import junit.framework.TestCase
import org.junit.Before
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
class ArtistDetailFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testListAlbumDetails() {
        onView(withId(R.id.btn_login_collector)).perform(click())
        onView(withId(R.id.albumsFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.artistsFragment)).perform(click())
        Thread.sleep(5000)
        onView(withIndex(withId(R.id.ly), 0)).perform(click());
        Thread.sleep(5000)
        onView(withId(R.id.textView6))?.check(matches(isDisplayed()))
    }
}