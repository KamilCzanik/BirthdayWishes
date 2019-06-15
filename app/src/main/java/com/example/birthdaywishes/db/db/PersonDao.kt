package com.example.birthdaywishes.db.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.birthdaywishes.data.Person

@Dao
interface PersonDao {

    @Insert fun insert(person: Person) : Long
    @Delete fun delete(person: Person)
    @Update fun update(person: Person)

    @Query("SELECT * FROM person_table ORDER BY name")
    fun getAllPeople() : LiveData<List<Person>>
}