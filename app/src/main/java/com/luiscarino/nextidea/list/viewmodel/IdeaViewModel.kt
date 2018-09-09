package com.luiscarino.nextidea.list.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.luiscarino.nextidea.list.model.room.entity.Category
import com.luiscarino.nextidea.list.model.room.entity.Idea
import com.luiscarino.nextidea.list.model.room.entity.Status
import com.luiscarino.nextidea.list.model.room.repository.IdeaRepository
import kotlinx.coroutines.experimental.launch

class IdeaViewModel(private val ideaRepository: IdeaRepository, nextIdeaApp: Application)
    : AndroidViewModel(nextIdeaApp) {

    private var categories: LiveData<List<Category>>? = null
    private var status: LiveData<List<Status>>? = null
    private var ideas: LiveData<List<Idea>>? = null
    private val cachedStatus : List<Status>? = null
    var selectedCategory: Category? = null
    var selectedStatus: Status? = null

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
        selectedCategory =  categories?.value?.get(position)
        return selectedCategory
    }

}