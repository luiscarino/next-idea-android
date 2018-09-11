package com.luiscarino.nextidea.list.model.room.repository

import android.app.Application
import com.luiscarino.nextidea.list.model.room.NextIdeaDatabase
import com.luiscarino.nextidea.list.model.room.dao.CategoryDao
import com.luiscarino.nextidea.list.model.room.dao.IdeaDao
import com.luiscarino.nextidea.list.model.room.dao.StatusDao
import com.luiscarino.nextidea.list.model.room.entity.Idea

/**
 * Abstracts access to data source.
 */
class IdeaRepository(application: Application) {

    private var ideaDao: IdeaDao?
    private var categoryDao : CategoryDao?
    private var statusDao : StatusDao?

    init {
        val db = NextIdeaDatabase.getInstance(application.applicationContext)
        ideaDao = db?.ideaDao()
        categoryDao = db?.categoryDao()
        statusDao = db?.statusDao()
    }

    fun getAllIdeas() = ideaDao?.geAllIdeas()

    fun insert(idea:Idea) {
        ideaDao?.insert(idea)
    }

    fun getAllCategories() = categoryDao?.geAllCategories()

    fun getAllStatus() = statusDao?.getAll()

    fun get(id:Long) = ideaDao?.get(id)

    fun update(idea: Idea) = ideaDao?.update(idea)

    fun delete(idea:Idea) = ideaDao?.delete(idea)
}