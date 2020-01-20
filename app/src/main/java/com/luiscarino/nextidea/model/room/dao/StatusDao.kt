package com.luiscarino.nextidea.model.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.luiscarino.nextidea.model.room.entity.Status

@Dao
interface StatusDao {

    @Insert
    fun insert(status: Status)

    @Query("SELECT * from status_table")
    fun getAll() : LiveData<List<Status>>

    @Query("DELETE FROM status_table")
    fun deleteAll()

    @Update
    fun update(status: Status)

    @Delete
    fun delete(status: Status)

}