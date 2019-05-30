package com.example.birthdaywishes.db

import androidx.room.TypeConverter
import com.example.birthdaywishes.POJO.Birthday

class BirthdayTypeConverter {

    companion object {

        @TypeConverter
        fun birthdayToString(birthday: Birthday) = birthday.toJsonString()
    }
}