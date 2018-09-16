package com.luiscarino.nextidea

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.luiscarino.nextidea.view.list.view.ListActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NextIdeaTestSuite {


    @Rule
    @JvmField
    var activityRule: ActivityTestRule<ListActivity> = ActivityTestRule(ListActivity::class.java)

    @Before
    fun init() {

    }


    @Test
    fun test_empty_screen() {

    }


    @Test
    fun test_list_results() {

    }

    @Test
    fun test_add_new() {
        onView(withId(R.id.fab)).perform(click())
    }

    @Test
    fun test_update() {
    }

    @Test
    fun test_delete() {

    }
}