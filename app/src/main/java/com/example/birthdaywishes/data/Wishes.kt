package com.example.birthdaywishes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishes_table")
open class Wishes(val content: String) {
    @PrimaryKey var id: Int? = null

    override fun equals(other: Any?): Boolean {
        return content.contentEquals((other as Wishes).content)
    }
}