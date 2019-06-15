package com.example.birthdaywishes.data

import java.io.Serializable
import java.text.DateFormatSymbols
import java.util.*

data class Birthday constructor(val day: Int,val month: Int) : Serializable{

    companion object {
        fun isDateValid(day: Int,month: Int) = day in 1..DaysInMonth[month] && month in 1..12
    }

    fun toJsonString() = "{\"day\":$day,\"month\":$month}"

    fun toDateString() = "$day ${DateFormatSymbols().months[month-1]}"

    fun isToday() : Boolean {
        val today = Calendar.getInstance()
        return today.get(Calendar.MONTH) == month-1 && today.get(Calendar.DAY_OF_MONTH) == day
    }
}