package com.example.birthdaywishes.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "person_table")
data class Person(val name: String, val birthday: Birthday, val phoneNumber: String) : Serializable{

    @PrimaryKey(autoGenerate = true) var id: Int? = null

}