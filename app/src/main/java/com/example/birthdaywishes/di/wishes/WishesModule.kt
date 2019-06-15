package com.example.birthdaywishes.di.wishes

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.birthdaywishes.db.WishesRepository
import com.example.birthdaywishes.ui.WishesFragment
import com.example.birthdaywishes.viewmodel.WishesViewModel
import dagger.Module
import dagger.Provides

@Module
class WishesModule(private val application: Application) {

    @Provides
    fun providesViewModel(repository: WishesRepository) : WishesFragment.ViewModel {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(WishesViewModel::class.java)
        viewModel.repository = repository
        return viewModel
    }
}