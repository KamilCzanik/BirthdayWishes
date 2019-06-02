package com.example.birthdaywishes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.birthdaywishes.pojo.Wishes
import com.example.birthdaywishes.repository.WishesRepository
import com.example.birthdaywishes.ui.WishesFragment
import javax.inject.Inject

class WishesViewModel(application: Application) : AndroidViewModel(application),WishesFragment.ViewModel {

    @Inject lateinit var wishesRepository: WishesRepository
    override val wishes by lazy { wishesRepository.wishes }

    override fun add(wishes: Wishes) {
        wishesRepository.add(wishes)
    }

    override fun delete(wishes: Wishes) {
        wishesRepository.delete(wishes)
    }
}