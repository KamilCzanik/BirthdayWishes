package com.example.birthdaywishes.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "person_table")
class Person(val name: String, val birthday: Birthday, val phoneNumber: String){

    @PrimaryKey(autoGenerate = true) var id: Int? = null

}