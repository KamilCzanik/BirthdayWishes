package com.example.birthdaywishes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.birthdaywishes.data.Wishes
import com.example.birthdaywishes.db.WishesRepository
import com.example.birthdaywishes.ui.AddWishesFragment
import javax.inject.Inject

class AddWishesViewModel(application: Application) : AndroidViewModel(application),AddWishesFragment.ViewModel {

    @Inject lateinit var repository: WishesRepository

    override fun add(wishes: Wishes) {
        repository.add(wishes)
    }
}