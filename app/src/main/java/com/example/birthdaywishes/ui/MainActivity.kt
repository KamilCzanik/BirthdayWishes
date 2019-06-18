package com.example.birthdaywishes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.birthdaywishes.R
import com.example.birthdaywishes.data.Wishes
import com.example.birthdaywishes.db.WishesRepository
import com.example.birthdaywishes.db.data.BirthdayWishesDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val FIRST_RUN_PREFS = "first_run"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainActivity_toolbar)
        importDefaultWishes()
    }

    private fun importDefaultWishes() {
        val prefs = getSharedPreferences("com.example.birthdaywishes.ui.MainActivity",MODE_PRIVATE)
        if (prefs.getBoolean(FIRST_RUN_PREFS, true)) {
            insertWishes()
            prefs.edit().putBoolean(FIRST_RUN_PREFS, false).apply()
        }
    }

    private fun insertWishes() {
        val wishesContents = resources.getStringArray(R.array.wishes_samples)
        val wishesDao = BirthdayWishesDatabase.getInstance(this).wishesDao()
        val repository = WishesRepository(wishesDao)

        wishesContents.forEach { content -> repository.add(Wishes(content)) }
    }
}
