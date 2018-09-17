package com.luiscarino.nextidea

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.luiscarino.nextidea.model.room.DefaultValues
import com.luiscarino.nextidea.model.room.NextIdeaDatabase
import com.luiscarino.nextidea.model.room.entity.Category
import com.luiscarino.nextidea.model.room.entity.Status
import com.luiscarino.nextidea.view.list.view.ListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.inject
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
class NextIdeaTestSuite : KoinTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<ListActivity> = ActivityTestRule(ListActivity::class.java)

    private val nextIdeaDatabase: NextIdeaDatabase by inject()

    @Before
    fun init() {
        initDatabase()
    }

    @After
    fun finalize() {
        nextIdeaDatabase.ideaDao().deleteAll()
    }

    @Test
    fun test_empty_screen() {
        onView(withId(R.id.emptyListTextView))
                .check(matches(isDisplayed()))
    }

    @Test
    fun test_add_new() {
        // navigate to add
        onView(withId(R.id.fab)).perform(click())

        val title = "Idea title"
        val description = "This is the description title"

        onView(withId(R.id.titleTextView))
                .perform(typeText(title))


        onView(withId(R.id.descriptionTextView))
                .perform(click())
                .perform(typeText(description))

        onView(withId(R.id.statusTextView))
                .perform(click())
                .check(matches(withText(DefaultValues.STATUS_IN_PROGRESS)))

        onView(withId(R.id.categoryNameTextView))
                .perform(click())

        onView(withText("Category"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))

        onView(withText("Technology"))
                .perform(click())

        // save
        onView(withId(R.id.action_save)).perform(click())

        // check on navigate back the added entry is displayed
        onView(withText(title)).check(matches(isDisplayed()))


    }

    @Test
    fun test_update() {

        //add
        test_add_new()

        //update
        val title = "Updated Idea title"
        val description = "Updated description"
        onView(withText("Idea title"))
                .perform(click())

        onView(withId(R.id.titleTextView))
                .perform(replaceText(title))


        onView(withId(R.id.descriptionTextView))
                .perform(replaceText(description))

        onView(withId(R.id.categoryNameTextView))
                .perform(click())

        onView(withText("Category"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))

        onView(withText("Other"))
                .perform(click())


        onView(withId(R.id.statusTextView))
                .perform(click())
                .check(matches(withText(DefaultValues.STATUS_DONE)))

        // save
        onView(withId(R.id.action_save)).perform(click())

        // check on navigate back the added entry is displayed
        onView(withText(title)).check(matches(isDisplayed()))
        onView(withText("Other")).check(matches(isDisplayed()))

    }

    @Test
    fun test_delete() {
        //add
        test_add_new()

        onView(withText("Idea title"))
                .perform(click())

        onView(withId(R.id.action_delete))
                .perform(click())

        onView(withId(R.id.emptyListTextView))
                .check(matches(isDisplayed()))

    }

    private fun initDatabase() {

        val categoryDao = nextIdeaDatabase.categoryDao()
        categoryDao.deleteAll()
        categoryDao.insert(Category(DefaultValues.CATEGORY_OTHER, "ic_twotone_other"))
        categoryDao.insert(Category(DefaultValues.CATEGORY_ART, "ic_twotone_art"))
        categoryDao.insert(Category(DefaultValues.CATEGORY_BUSINESS, "ic_twotone_business"))
        categoryDao.insert(Category(DefaultValues.CATEGORY_FOOD, "ic_twotone_food"))
        categoryDao.insert(Category(DefaultValues.CATEGORY_GAMING, "ic_twotone_games"))
        categoryDao.insert(Category(DefaultValues.CATEGORY_MUSIC, "ic_twotone_music"))
        categoryDao.insert(Category(DefaultValues.CATEGORY_PHOTOGRAPHY, "ic_twotone_photography"))
        categoryDao.insert(Category(DefaultValues.CATEGORY_PRODUCT, "ic_twotone_product"))
        categoryDao.insert(Category(DefaultValues.CATEGORY_TECH, "ic_twotone_tech"))
        categoryDao.insert(Category(DefaultValues.CATEGORY_WRITING, "ic_twotone_writing"))

        val statusDao = nextIdeaDatabase.statusDao()
        statusDao.deleteAll()
        statusDao.insert(Status(DefaultValues.STATUS_NOT_STARTED))
        statusDao.insert(Status(DefaultValues.STATUS_IN_PROGRESS))
        statusDao.insert(Status(DefaultValues.STATUS_DONE))
        statusDao.insert(Status(DefaultValues.STATUS_BLOCKED))


    }
}