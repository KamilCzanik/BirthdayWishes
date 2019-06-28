package com.example.birthdaywishes.di.addWishes

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.birthdaywishes.repository.WishesRepository
import com.example.birthdaywishes.ui.AddWishesFragment
import com.example.birthdaywishes.viewmodel.AddWishesViewModel
import dagger.Module
import dagger.Provides

@Module
class AddWishesModule(private val app: Application) {

    @Provides
    fun providesViewModel(repository: WishesRepository) : AddWishesFragment.ViewModel {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(app).create(
            AddWishesViewModel::class.java)
        viewModel.repository = repository
        return viewModel
    }
}