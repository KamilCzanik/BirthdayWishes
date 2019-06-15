package com.example.birthdaywishes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "person_table")
data class Person(
    val name: String,
    val birthday: Birthday, val phoneNumber: String,
    @PrimaryKey(autoGenerate = true) var id: Long = 0) : Serializable{

    fun isDataValid() = name.isNotEmpty() && Birthday.isDateValid(birthday.day,birthday.month)

    fun hasBirthday() = birthday.isToday()
}