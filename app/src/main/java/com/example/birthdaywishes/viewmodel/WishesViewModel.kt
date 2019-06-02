package com.example.birthdaywishes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.birthdaywishes.pojo.Wishes
import com.example.birthdaywishes.repository.WishesRepository
import com.example.birthdaywishes.ui.WishesFragment
import javax.inject.Inject

class WishesViewModel(application: Application) : AndroidViewModel(application),WishesFragment.ViewModel {

    @Inject lateinit var repository: WishesRepository
    override val wishes by lazy { repository.wishes }

    override fun add(wishes: Wishes) {
        repository.add(wishes)
    }

    override fun delete(wishes: Wishes) {
        repository.delete(wishes)
    }
}