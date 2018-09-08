package com.luiscarino.nextidea.list.model.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.luiscarino.nextidea.list.model.room.entity.Status

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