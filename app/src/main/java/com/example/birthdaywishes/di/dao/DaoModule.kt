package com.example.birthdaywishes.di.dao

import android.app.Application
import com.example.birthdaywishes.db.data.BirthdayWishesDatabase
import dagger.Module
import dagger.Provides

@Module
class DaoModule(private val application: Application) {

    @Provides
    fun providesDb() = BirthdayWishesDatabase.getInstance(application)

    @Provides
    fun providesPersonDao(birthdayWishesDatabase: BirthdayWishesDatabase) = birthdayWishesDatabase.personDao()

    @Provides
    fun providesWishesDao(birthdayWishesDatabase: BirthdayWishesDatabase) = birthdayWishesDatabase.wishesDao()
}