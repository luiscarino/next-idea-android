package com.luiscarino.nextidea

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.luiscarino.nextidea.list.model.room.*
import com.luiscarino.nextidea.list.model.room.dao.CategoryDao
import com.luiscarino.nextidea.list.model.room.dao.IdeaDao
import com.luiscarino.nextidea.list.model.room.dao.StatusDao
import com.luiscarino.nextidea.list.model.room.entity.Category
import com.luiscarino.nextidea.list.model.room.entity.Idea
import com.luiscarino.nextidea.list.model.room.entity.Status
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Test CRUD transactions in ROOM
 */
@RunWith(AndroidJUnit4::class)
class IdeaViewModelTest {

    private var ideaDao: IdeaDao? = null
    private var statusDao: StatusDao? = null
    private var categoryDao : CategoryDao? = null
    private var db: NextIdeaDatabase? = null

    @Before
    fun create_database() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(appContext, NextIdeaDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        ideaDao = db?.ideaDao()
        categoryDao = db?.categoryDao()
        statusDao = db?.statusDao()
    }

    @After
    fun close_database() {
        db?.close()
    }

    @Test
    fun create_and_get_idea_test() {
        categoryDao?.insert(generateCategory())
        categoryDao?.insert(generateCategory())
        categoryDao?.insert(generateCategory())
        statusDao?.insert(generateStatus())
        statusDao?.insert(generateStatus())
        statusDao?.insert(generateStatus())
        ideaDao?.insert(generateIdea())
        ideaDao?.insert(generateIdea())

        val ideas = LiveDataTestUtil.getValue(ideaDao?.geAllIdeas())
        assertEquals(ideas[0].title, "Title Idea 1")
    }

    @Test
    fun update_idea_test() {
        ideaDao?.insert(generateIdea())
        val ideas = LiveDataTestUtil.getValue(ideaDao?.geAllIdeas())
        assertEquals(ideas[0].title, "Title Idea 1")
        assertEquals(ideas.size, 1)

        val newIdea = ideas[0]
        newIdea.title = "New title"

        ideaDao?.update(newIdea)
        assertEquals(ideas[0].title, "New title")
        assertEquals(ideas.size, 1)
    }

    @Test
    fun read_all_ideas_test() {
        ideaDao?.insert(generateIdea())
        ideaDao?.insert(generateIdea())
        ideaDao?.insert(generateIdea())
        ideaDao?.insert(generateIdea())

        val ideas = LiveDataTestUtil.getValue(ideaDao?.geAllIdeas())
        assertEquals(ideas.size, 4)
    }

    @Test
    fun delete_single_test() {
        ideaDao?.insert(generateIdea())
        ideaDao?.insert(generateIdea())
        ideaDao?.insert(generateIdea())
        var ideas = LiveDataTestUtil.getValue(ideaDao?.geAllIdeas())
        assertTrue(ideas.isNotEmpty())

        ideaDao?.delete(ideas[2])
        ideas = LiveDataTestUtil.getValue(ideaDao?.geAllIdeas())
        assertEquals(ideas.size,2)

    }

    @Test
    fun delete_all_test() {
        ideaDao?.insert(generateIdea())

        ideaDao?.deleteAll()
        val ideas = LiveDataTestUtil.getValue(ideaDao?.geAllIdeas())
        assertTrue(ideas.isEmpty())
    }

    private fun generateIdea() = Idea("Title Idea 1", "Desc", Status("IN PROGRESS"), Category("PERSONAL"))

    private fun generateCategory() = Category("Personal")

    private fun generateStatus() = Status("In progress")


}
