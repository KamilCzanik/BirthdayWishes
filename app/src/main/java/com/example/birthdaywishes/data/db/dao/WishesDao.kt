package com.example.birthdaywishes.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.birthdaywishes.data.Wishes

@Dao
interface WishesDao {

    @Insert fun insert(wishes: Wishes)
    @Delete fun delete(wishes: Wishes)
    @Update fun update(wishes: Wishes)

    @Query("SELECT * FROM wishes_table ORDER BY id DESC")
    fun getAllWishes() : LiveData<List<Wishes>>
}