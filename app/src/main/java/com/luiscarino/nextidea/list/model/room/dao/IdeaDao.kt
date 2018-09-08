package com.luiscarino.nextidea.list.model.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.luiscarino.nextidea.list.model.room.entity.Idea

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


}