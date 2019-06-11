package com.example.birthdaywishes.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BirthdayAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val BIRTHDAY_INTENT = "com.example.birthdaywishes.system.BIRTHDAY_BROADCAST_INTENT"
        const val PERSON_NAME_EXTRA = "com.example.birthdaywishes.systemService.BirthdayNotificationScheduler.PERSON_NAME_EXTRA"
        const val PERSON_ID_EXTRA = "com.example.birthdaywishes.systemService.BirthdayNotificationScheduler.PERSON_ID_EXTRA"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val extra = intent.extras
        extra?.let {
            val name = extra.getString(PERSON_NAME_EXTRA) ?: ""
            val id = extra.getLong(PERSON_ID_EXTRA,0)
            NotificationPublisher(context).postNotification(name, id)
        }
    }
}