package com.example.birthdaywishes.data

abstract class Months {

    companion object {

        const val MIN_DAYS: Int = 1

        private val monthDayCountArr: IntArray = intArrayOf(31,29,31,30,31,30,31,31,30,31,30,31)

        operator fun get(month: Int) = Month(getDaysCountFor(month))

        private fun getDaysCountFor(month: Int) = if(month in 1..12) monthDayCountArr[month-1] else 0
    }

    data class Month(val daysCount: Int)
}