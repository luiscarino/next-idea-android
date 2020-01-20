package com.luiscarino.nextidea.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luiscarino.nextidea.model.room.entity.Category
import com.luiscarino.nextidea.model.room.entity.Idea
import com.luiscarino.nextidea.model.room.entity.Status
import com.luiscarino.nextidea.model.room.repository.IdeaRepository
import kotlinx.coroutines.launch

class IdeaViewModel(private val ideaRepository: IdeaRepository) : ViewModel() {

    var categories: LiveData<List<Category>> = ideaRepository.getAllCategories()
    var status: LiveData<List<Status>> = ideaRepository.getAllStatus()
    var ideas: LiveData<List<Idea>> = ideaRepository.getAllIdeas()

    var selectedCategory: Category? = null
    var selectedStatus: Status? = null
    var detailIdea: Idea? = null
    var isEditMode: Boolean = false

    fun insert(idea: Idea) {
        viewModelScope.launch {
            ideaRepository.insert(idea)
        }
    }

    fun update(idea: Idea) {
        viewModelScope.launch {
            ideaRepository.update(idea)
        }
    }

    fun getIdeaById(id: Long): LiveData<Idea> {
        return ideaRepository.get(id)
    }

    fun delete(id: Long): LiveData<List<Idea>> {
        viewModelScope.launch {
            val toDelete = ideaRepository.getIdeaById(id)
            ideaRepository.delete(toDelete)
        }
        return ideas
    }


    fun updateSelectedCategory(position: Int): Category? {
        selectedCategory = categories.value?.get(position)
        return selectedCategory
    }

}