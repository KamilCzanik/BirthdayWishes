package com.example.birthdaywishes.di.personModification.addPerson

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.birthdaywishes.repository.PersonRepository
import com.example.birthdaywishes.systemService.BirthdayAlarmScheduler
import com.example.birthdaywishes.ui.AddPersonFragment
import com.example.birthdaywishes.viewmodel.personModification.AddPersonViewModel
import dagger.Module
import dagger.Provides

@Module
class AddPersonModule(private val application: Application){

    @Provides
    fun providesViewModel(repository: PersonRepository,scheduler: BirthdayAlarmScheduler) : AddPersonFragment.ViewModel {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(AddPersonViewModel::class.java)
        viewModel.personRepository = repository
        viewModel.scheduler = scheduler
        return viewModel
    }
}