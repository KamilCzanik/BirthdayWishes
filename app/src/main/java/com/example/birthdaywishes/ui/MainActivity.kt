package com.example.birthdaywishes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.birthdaywishes.R
import com.example.birthdaywishes.pojo.Wishes
import com.example.birthdaywishes.repository.WishesRepository
import com.example.birthdaywishes.repository.db.BirthdayWishesDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainActivity_toolbar)
        importDefaultWishes()
    }

    private fun importDefaultWishes() {
        val prefs = getSharedPreferences("com.example.birthdaywishes.ui.MainActivity",MODE_PRIVATE)
        if (prefs.getBoolean("first_run", true)) {
            insertWishes()
            prefs.edit().putBoolean("first_run", false).commit()
        }
    }

    private fun insertWishes() {
        val wishesContents = resources.getStringArray(R.array.wishes_samples)
        val wishesDao = BirthdayWishesDatabase.getInstance(this).wishesDao()
        val repository = WishesRepository(wishesDao)

        wishesContents.forEach { content -> repository.add(Wishes(content)) }
    }
}
