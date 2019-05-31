package com.example.birthdaywishes.pojo

abstract class DaysOfMonth {
    companion object {
        private val monthDayCountArr: IntArray = intArrayOf(31,29,31,30,31,30,31,31,30,31,30,31)
        operator fun get(i: Int) = if(i in 1..12) monthDayCountArr[i-1] else 0
    }

}