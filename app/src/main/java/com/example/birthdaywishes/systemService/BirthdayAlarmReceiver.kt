package com.example.birthdaywishes.systemService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.systemService.BirthdayAlarmScheduler.Companion.PERSON_EXTRA

object BirthdayAlarmReceiver : BroadcastReceiver() {

    const val CHANNEL_ID = 0

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.hasExtra(PERSON_EXTRA))
            NotificationPublisher.postNotification(
                context,
                intent.getSerializableExtra(PERSON_EXTRA) as Person
            )
    }


}