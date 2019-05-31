package com.example.birthdaywishes.repository.db

import androidx.room.TypeConverter
import com.example.birthdaywishes.pojo.Birthday

class BirthdayTypeConverter {

    companion object {

        @TypeConverter
        fun birthdayToString(birthday: Birthday) = birthday.toJsonString()
    }
}