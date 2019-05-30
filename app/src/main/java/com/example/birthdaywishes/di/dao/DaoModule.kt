package com.example.birthdaywishes.di.dao

import android.app.Application
import com.example.birthdaywishes.repository.db.BirthdayWishesDatabase
import dagger.Module
import dagger.Provides

@Module
class DaoModule(private val application: Application) {

    @Provides
    fun providesPersonDao(birthdayWishesDatabase: BirthdayWishesDatabase) = birthdayWishesDatabase.personDao()

    @Provides
    fun providesDb() = BirthdayWishesDatabase.getInstance(application)
}