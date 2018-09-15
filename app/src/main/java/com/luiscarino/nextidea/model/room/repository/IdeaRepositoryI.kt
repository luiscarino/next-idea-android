package com.luiscarino.nextidea.model.room.repository

interface IdeaRepositoryI {

    fun getAll()

    fun delete(id : Long)

    fun update(id : Long)

    fun deleteAll()
}