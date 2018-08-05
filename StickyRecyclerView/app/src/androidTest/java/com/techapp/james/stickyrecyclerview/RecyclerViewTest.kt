package com.techapp.james.stickyrecyclerview

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class RecyclerViewTest {
    private val BELOW_FOLD = 0
    @get:Rule
    public var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
            MainActivity::class.java)

    @Test
    fun scrollToItemBelowFold() {
        mActivityRule.activity
        var viewInteraction = onView(withId(R.id.recyclerList))
        viewInteraction.perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(BELOW_FOLD))
        val data = "a"
        onView(withText(data)).check(matches(isDisplayed()))
        //one of data is "a"
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("a")))

//.perform(click())
    }
}