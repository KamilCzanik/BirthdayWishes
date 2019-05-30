package com.example.birthdaywishes.POJO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishes_table")
data class Wishes(val content: String) {
    @PrimaryKey var id: Int? = null
}