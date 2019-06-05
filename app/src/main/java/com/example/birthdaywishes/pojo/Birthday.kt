package com.example.birthdaywishes.pojo

import android.text.format.DateUtils
import java.io.Serializable
import java.text.DateFormatSymbols
import java.util.*

data class Birthday constructor(val day: Int,val month: Int) : Serializable{

    companion object {
        fun isDateValid(day: Int,month: Int) = day in 1..DaysOfMonth[month] && month in 1..12
    }

    fun toJsonString() = "{\"day\":$day,\"month\":$month}"

    fun toDateString() = "$day ${DateFormatSymbols().months[month-1]}"

    fun isToday() = DateUtils.isToday(
        Calendar.getInstance().apply {
            set(Calendar.MONTH,month)
            set(Calendar.DAY_OF_MONTH,day)
            set(Calendar.HOUR_OF_DAY,8)
        }.timeInMillis)
}