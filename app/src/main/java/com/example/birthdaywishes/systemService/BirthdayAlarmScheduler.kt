package com.example.birthdaywishes.systemService

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.birthdaywishes.pojo.Person
import java.util.*
import javax.inject.Inject

class BirthdayAlarmScheduler @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val calendar: Calendar) {

    companion object {
        const val PERSON_EXTRA = "com.example.birthdaywishes.systemService.BirthdayNotificationScheduler.PERSON_EXTRA"
    }

    fun scheduleBirthdayAlarm(person: Person) {
        calendar.apply {
            set(Calendar.MONTH,person.birthday.month)
            set(Calendar.DAY_OF_MONTH,person.birthday.day)
            set(Calendar.HOUR_OF_DAY,8)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            calendar.timeInMillis,
            0,
            getPendingIntent(person)
        )
    }

    fun cancelPreviousBirthdayAlarm(person: Person) {
        alarmManager.cancel(getPendingIntent(person))
    }

    private fun getPendingIntent(person: Person) : PendingIntent = PendingIntent.getBroadcast(
        context,1,getAlarmIntent(person),0)

    private fun getAlarmIntent(person: Person) =
        Intent(context, BirthdayAlarmReceiver::class.java).apply {
        putExtra(PERSON_EXTRA,person)
    }
}