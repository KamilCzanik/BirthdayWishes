package com.example.birthdaywishes.repository.db

import androidx.room.TypeConverter
import com.beust.klaxon.Klaxon
import com.example.birthdaywishes.pojo.Birthday

class BirthdayTypeConverter {

    @TypeConverter
    fun birthdayToJsonString(birthday: Birthday) : String = birthday.toJsonString()

    @TypeConverter
    fun stringToBirthday(birthdayJson: String) : Birthday = Klaxon().parse<Birthday>(birthdayJson)!!
}