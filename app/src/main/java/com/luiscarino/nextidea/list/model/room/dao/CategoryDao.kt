package com.luiscarino.nextidea.list.model.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.luiscarino.nextidea.list.model.room.entity.Category

@Dao
interface CategoryDao {

    @Insert
    fun insert(category: Category)

    @Query("SELECT * from category_table")
    fun geAllCategories() : LiveData<List<Category>>

    @Query("DELETE FROM category_table")
    fun deleteAll()

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)
}