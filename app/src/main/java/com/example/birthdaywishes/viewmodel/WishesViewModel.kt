package com.example.birthdaywishes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.birthdaywishes.data.Wishes
import com.example.birthdaywishes.db.WishesRepository
import com.example.birthdaywishes.ui.WishesFragment
import javax.inject.Inject

class WishesViewModel(application: Application) : AndroidViewModel(application),WishesFragment.ViewModel {

    @Inject lateinit var repository: WishesRepository
    override val allItems by lazy { repository.wishes }

    override fun add(wishes: Wishes) { repository.add(wishes) }

    override fun delete(item: Wishes) { repository.delete(item) }
}