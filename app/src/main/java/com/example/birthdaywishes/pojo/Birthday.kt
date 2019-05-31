package com.example.birthdaywishes.pojo

data class Birthday constructor(val day: Int,val month: Int) {

    companion object {
        fun isDateValid(day: Int,month: Int) = day in 1..DaysOfMonth[month] && month in 1..12
        fun of(day: Int,month: Int) = if(isDateValid(
                day,
                month
            )
        ) Birthday(day, month) else Birthday(1, 1)
    }

    fun toJsonString() = "{\"day\":$day,\"month\":$month}"
}