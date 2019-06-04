package com.example.birthdaywishes.di.personModification.addPerson

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.birthdaywishes.repository.PersonRepository
import com.example.birthdaywishes.ui.personModification.AddPersonFragment
import com.example.birthdaywishes.viewmodel.AddPersonViewModel
import dagger.Module
import dagger.Provides

@Module
class AddPersonModule(private val application: Application){

    @Provides
    fun providesViewModel(repository: PersonRepository) : AddPersonFragment.ViewModel {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(AddPersonViewModel::class.java)
        viewModel.personRepository = repository
        return viewModel
    }
}