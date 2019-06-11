package com.example.birthdaywishes.pojo

abstract class DaysInMonth {

    companion object {

        const val MAX_DAYS: Int = 31
        const val MIN_DAYS: Int = 1

        private val monthDayCountArr: IntArray = intArrayOf(31,29,31,30,31,30,31,31,30,31,30,31)

        operator fun get(i: Int) = if(i in 1..12) monthDayCountArr[i-1] else 0
    }

}