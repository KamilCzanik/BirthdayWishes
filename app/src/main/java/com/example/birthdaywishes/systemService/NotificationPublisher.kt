package com.example.birthdaywishes.systemService

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.ui.MainActivity


class NotificationPublisher {

    companion object {

        fun postNotification(context: Context, person: Person) {
            val notification = NotificationCompat.Builder(context)
                .setSmallIcon(com.example.birthdaywishes.R.drawable.ic_giftcard)
                .setContentTitle(context.resources.getString(com.example.birthdaywishes.R.string.birthday_reminder))
                .setContentText(person.name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(
                    getBirthdayPendingIntent(
                        context
                    )
                )
                .build()

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                person.id!!/*as channel*/,
                notification
            )
        }

        private fun getBirthdayPendingIntent(context: Context) =
            PendingIntent.getActivity(context, 0,
                getBirthdayIntent(context), PendingIntent.FLAG_UPDATE_CURRENT)

        private fun getBirthdayIntent(context: Context) = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_FROM_BACKGROUND or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }
}