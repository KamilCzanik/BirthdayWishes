package com.example.birthdaywishes.di.people

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.birthdaywishes.repository.PeopleRepository
import com.example.birthdaywishes.repository.db.BirthdayWishesDatabase
import com.example.birthdaywishes.viewmodel.PeopleViewModel
import dagger.Module
import dagger.Provides

@Module
class PeopleModule(private val application: Application) {

    @Provides
    fun providesViewModel(repository: PeopleRepository) : PeopleViewModel {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(PeopleViewModel::class.java)
        viewModel.repository = repository
        return viewModel
    }

}