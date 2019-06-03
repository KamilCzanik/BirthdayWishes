package com.example.birthdaywishes.di.personModification.editPerson

import android.app.Application
import dagger.Module

@Module
class EditPersonModule(private val application: Application) {

    /* TODO @Provides
    fun providesViewModel(repository: PersonRepository) : EditPersonFragment.ViewModel {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(::class.java)
        viewModel.repository = repository
        return viewModel
    } */
}