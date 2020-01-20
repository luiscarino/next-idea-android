package com.luiscarino.nextidea.model.room.repository

import com.luiscarino.nextidea.model.room.dao.CategoryDao
import com.luiscarino.nextidea.model.room.dao.IdeaDao
import com.luiscarino.nextidea.model.room.dao.StatusDao
import com.luiscarino.nextidea.model.room.entity.Idea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Abstracts access to data source.
 */
class IdeaRepository(private val ideaDao: IdeaDao,
                     private val categoryDao: CategoryDao,
                     private val statusDao: StatusDao) {

     fun getAllIdeas() = ideaDao.geAllIdeas()

    suspend fun insert(idea: Idea) = withContext(Dispatchers.IO) { ideaDao.insert(idea) }

    fun getAllCategories() = categoryDao.geAllCategories()

    fun getAllStatus() = statusDao.getAll()

    fun get(id: Long) = ideaDao.get(id)

    suspend fun update(idea: Idea) = withContext(Dispatchers.IO) { ideaDao.update(idea) }

    suspend fun delete(idea: Idea) = withContext(Dispatchers.IO) { ideaDao.delete(idea) }

    suspend fun getIdeaById(id: Long) = withContext(Dispatchers.IO) {ideaDao.getById(id) }
}