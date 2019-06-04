package com.example.birthdaywishes.di

import android.app.AlarmManager
import android.content.Context
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class SystemServiceModule(private val context: Context) {

    @Provides
    fun providesContext() = context

    @Provides
    fun providesCalendar() = Calendar.getInstance()

    @Provides
    fun providesAlarmManager() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
}