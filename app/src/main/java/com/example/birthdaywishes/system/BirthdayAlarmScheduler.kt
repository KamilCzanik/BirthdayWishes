package com.example.birthdaywishes.system

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.birthdaywishes.pojo.Person
import java.util.*
import javax.inject.Inject

class BirthdayAlarmScheduler @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager) {

    fun scheduleBirthdayAlarm(person: Person) {
        val today = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.MONTH,person.birthday.month-1)
            set(Calendar.DAY_OF_MONTH,person.birthday.day)
            set(Calendar.HOUR_OF_DAY,0)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,1)
            if(before(today)) add(Calendar.YEAR,1)
        }

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            getPendingIntent(person)
        )
    }

    private fun getPendingIntent(person: Person) = PendingIntent.getBroadcast(
        context,person.id.toInt(),getAlarmIntent(person),PendingIntent.FLAG_UPDATE_CURRENT)

    private fun getAlarmIntent(person: Person) : Intent {
        val intent = Intent(context,BirthdayAlarmReceiver::class.java)
        with(intent) {
            action = BirthdayAlarmReceiver.BIRTHDAY_INTENT
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(BirthdayAlarmReceiver.PERSON_NAME_EXTRA,person.name)
            putExtra(BirthdayAlarmReceiver.PERSON_ID_EXTRA,person.id)
        }
        return intent
    }
}