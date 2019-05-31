package com.example.birthdaywishes.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.birthdaywishes.pojo.Person

@Database(entities = [Person::class], version = 1, exportSchema = false)
@TypeConverters(BirthdayTypeConverter::class)
abstract class BirthdayWishesDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun wishesDao(): WishesDao

    companion object {
        private var instance: BirthdayWishesDatabase? = null

        @Synchronized
        fun getInstance(context: Context): BirthdayWishesDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    BirthdayWishesDatabase::class.java,
                    "birthday_wishes_database")
                    .fallbackToDestructiveMigration()
                    .build()

            return instance as BirthdayWishesDatabase
        }
    }
}