package com.example.birthdaywishes.system

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.birthdaywishes.ui.MainActivity


class NotificationPublisher(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun postNotification(personName: String, personId: Long) {
        val channel = getChannel(personName, personId)
        val notificationBuilder = NotificationCompat.Builder(context, channel)
            .setSmallIcon(com.example.birthdaywishes.R.drawable.ic_giftcard)
            .setContentTitle(context.resources.getString(com.example.birthdaywishes.R.string.birthday_reminder))
            .setContentText(personName)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(getBirthdayPendingIntent())

        createNotificationChannel(channel)
        NotificationManagerCompat.from(context).notify(personId.toInt(), notificationBuilder.build())
    }

    private fun getChannel(personName: String, personId: Long) = "${personName}_$personId"

    private fun getBirthdayPendingIntent() =
        PendingIntent.getActivity(context, 0, getBirthdayIntent(), PendingIntent.FLAG_UPDATE_CURRENT)

    private fun getBirthdayIntent() = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, "birthday_reminders", importance)

            notificationManager.createNotificationChannel(channel)
        }
    }
}