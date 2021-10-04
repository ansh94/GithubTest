package com.anshdeep.takehome

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anshdeep.takehome.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun emptyUserIdShowsErrorOnSearchButtonClick() {
        onView(withId(R.id.searchEditText)).perform(typeText(""))
        onView(withId(R.id.searchButton)).perform(click())
        onView(withText(R.string.enter_user_id)).check(matches(isDisplayed()))
    }

    @Test
    fun validUserIdShowsRepositories() {
        onView(withId(R.id.searchEditText)).perform(typeText("octocat"))
        onView(withId(R.id.searchButton)).perform(click())
        onView(withId(R.id.repositoryList)).check(matches(isDisplayed()))
    }

}