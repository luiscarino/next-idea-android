package com.luiscarino.nextidea.model.room.repository

import com.luiscarino.nextidea.model.room.dao.CategoryDao
import com.luiscarino.nextidea.model.room.dao.IdeaDao
import com.luiscarino.nextidea.model.room.dao.StatusDao
import com.luiscarino.nextidea.model.room.entity.Idea

/**
 * Abstracts access to data source.
 */
class IdeaRepository(private val ideaDao: IdeaDao,
                     private val categoryDao: CategoryDao,
                     private val statusDao: StatusDao) {

    fun getAllIdeas() = ideaDao.geAllIdeas()

    fun insert(idea: Idea) = ideaDao.insert(idea)

    fun getAllCategories() = categoryDao.geAllCategories()

    fun getAllStatus() = statusDao.getAll()

    fun get(id: Long) = ideaDao.get(id)

    fun update(idea: Idea) = ideaDao.update(idea)

    fun delete(idea: Idea) = ideaDao.delete(idea)

    fun getIdeaById(id: Long) = ideaDao.getById(id)
}