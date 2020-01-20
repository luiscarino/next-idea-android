package com.luiscarino.nextidea.model.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.luiscarino.nextidea.model.room.entity.Category

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