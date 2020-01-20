package com.luiscarino.nextidea.model.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.luiscarino.nextidea.model.room.entity.Idea

@Dao
interface IdeaDao {

    @Insert
    fun insert(idea: Idea)

    @Query("SELECT * FROM idea_table ORDER BY lastUpdatedDate DESC")
    fun geAllIdeas() : LiveData<List<Idea>>

    @Query("DELETE FROM idea_table")
    fun deleteAll()

    @Update
    fun update(idea: Idea)

    @Delete
    fun delete(idea: Idea)

    @Query("SELECT * FROM idea_table WHERE id = :id")
    fun get(id:Long) : LiveData<Idea>

    @Query("SELECT * FROM idea_table WHERE id = :id")
    fun getById(id:Long) : Idea



}