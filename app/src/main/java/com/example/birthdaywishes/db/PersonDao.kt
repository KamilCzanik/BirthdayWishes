package com.example.birthdaywishes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.birthdaywishes.POJO.Person

@Dao
interface PersonDao {

    @Insert fun insert(person: Person)
    @Delete fun delete(person: Person)
    @Update fun update(person: Person)

    @Query("SELECT * FROM person_table ORDER BY name")
    fun getAllPeople() : LiveData<List<Person>>
}