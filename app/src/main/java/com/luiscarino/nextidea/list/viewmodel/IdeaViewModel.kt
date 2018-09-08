package com.luiscarino.nextidea.list.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.luiscarino.nextidea.list.model.room.entity.Idea
import com.luiscarino.nextidea.list.model.room.repository.IdeaRepository
import kotlinx.coroutines.experimental.launch

class IdeaViewModel(private val ideaRepository: IdeaRepository, nextIdeaApp: Application)
    : AndroidViewModel(nextIdeaApp) {

    fun insert(idea:Idea) {
        launch {
            ideaRepository.insert(idea)
        }
    }

    fun update(idea: Idea) {
        launch { ideaRepository.update(idea) }
    }

    fun getAllIdeas() = ideaRepository.getAllIdeas()

    fun getAllCategories() = ideaRepository.getAllCategories()

    fun getAllStatus() = ideaRepository.getAllStatus()

    fun get(id:Long) = ideaRepository.get(id)

}
