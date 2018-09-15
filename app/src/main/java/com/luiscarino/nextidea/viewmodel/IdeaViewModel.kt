package com.luiscarino.nextidea.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.luiscarino.nextidea.model.room.entity.Category
import com.luiscarino.nextidea.model.room.entity.Idea
import com.luiscarino.nextidea.model.room.entity.Status
import com.luiscarino.nextidea.model.room.repository.IdeaRepository
import kotlinx.coroutines.experimental.launch

class IdeaViewModel(private val ideaRepository: IdeaRepository, nextIdeaApp: Application)
    : AndroidViewModel(nextIdeaApp) {

    private var categories: LiveData<List<Category>>? = null
    private var status: LiveData<List<Status>>? = null
    private var ideas: LiveData<List<Idea>>? = null

    var selectedCategory: Category? = null
    var selectedStatus: Status? = null
    var detailIdea: Idea? = null

    var isEditMode: Boolean = false

    fun insert(idea: Idea) {
        launch {
            ideaRepository.insert(idea)
        }
    }

    fun update(idea: Idea) {
        launch { ideaRepository.update(idea) }
    }

    fun getAllIdeas(): LiveData<List<Idea>>? {
        ideas = ideaRepository.getAllIdeas()
        return ideas
    }

    fun getIdeaById(id: Long): LiveData<Idea>? {
        return ideaRepository.get(id)
    }

    fun delete(id: Long): LiveData<List<Idea>>? {
        launch {
            val toDelete = ideaRepository.getIdeaById(id)
            if (toDelete != null) {
                ideaRepository.delete(toDelete)
            }
        }
        return getAllIdeas()
    }


    fun getAllCategories(): LiveData<List<Category>>? {
        categories = ideaRepository.getAllCategories()
        return categories
    }

    fun getAllStatus(): LiveData<List<Status>>? {
        status = ideaRepository.getAllStatus()
        return status
    }

    fun get(id: Long) = ideaRepository.get(id)

    fun updateSelectedCategory(position: Int): Category? {
        selectedCategory = categories?.value?.get(position)
        return selectedCategory
    }

}