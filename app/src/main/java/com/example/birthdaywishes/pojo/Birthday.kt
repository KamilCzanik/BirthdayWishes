package com.example.birthdaywishes.pojo

import java.io.Serializable
import java.text.DateFormatSymbols

data class Birthday constructor(val day: Int,val month: Int) : Serializable{

    companion object {
        fun isDateValid(day: Int,month: Int) = day in 1..DaysOfMonth[month] && month in 1..12
    }

    fun toJsonString() = "{\"day\":$day,\"month\":$month}"

    fun toDateString() = "$day  ${DateFormatSymbols().months[month-1]}"
}