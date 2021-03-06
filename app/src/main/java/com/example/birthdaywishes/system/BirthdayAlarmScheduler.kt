package com.example.birthdaywishes.system

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import com.example.birthdaywishes.data.Person
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject

class BirthdayAlarmScheduler @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager) {

    fun scheduleBirthdayAlarm(person: Person) =
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            getBirthdayCalendar(person).timeInMillis,
            DateUtils.YEAR_IN_MILLIS,
            getPendingIntent(person))

    private fun getBirthdayCalendar(person: Person): Calendar =
        getInstance().apply {
            set(MONTH,person.birthday.month-1)
            set(DAY_OF_MONTH,person.birthday.day)
            set(HOUR_OF_DAY,0)
            set(MINUTE,0)
            set(SECOND,1)
            if(before(getInstance())) add(YEAR,1)
        }

    private fun getPendingIntent(person: Person) =
        PendingIntent.getBroadcast(context,person.id.toInt(),getAlarmIntent(person),PendingIntent.FLAG_UPDATE_CURRENT)

    private fun getAlarmIntent(person: Person) =
        Intent(context,BirthdayAlarmReceiver::class.java).apply {
        action = BirthdayAlarmReceiver.BIRTHDAY_INTENT
        addCategory(Intent.CATEGORY_DEFAULT)
        putExtra(BirthdayAlarmReceiver.PERSON_NAME_EXTRA,person.name)
        putExtra(BirthdayAlarmReceiver.PERSON_ID_EXTRA,person.id)
    }
}