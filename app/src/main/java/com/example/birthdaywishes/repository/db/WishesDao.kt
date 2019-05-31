package com.example.birthdaywishes.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.birthdaywishes.pojo.Wishes

@Dao
interface WishesDao {

    @Insert fun insert(wishes: Wishes)
    @Delete fun delete(wishes: Wishes)
    @Update fun update(wishes: Wishes)

    @Query("SELECT * FROM wishes_table")
    fun getAllWishes() : LiveData<List<Wishes>>
}