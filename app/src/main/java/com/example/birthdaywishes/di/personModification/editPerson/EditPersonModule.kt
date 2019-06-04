package com.example.birthdaywishes.di.personModification.editPerson

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.birthdaywishes.repository.PersonRepository
import com.example.birthdaywishes.systemService.BirthdayAlarmScheduler
import com.example.birthdaywishes.ui.personModification.EditPersonFragment
import com.example.birthdaywishes.viewmodel.EditPersonViewModel
import dagger.Module
import dagger.Provides

@Module
class EditPersonModule(private val application: Application) {

    @Provides
    fun providesViewModel(repository: PersonRepository,scheduler: BirthdayAlarmScheduler) : EditPersonFragment.ViewModel {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(EditPersonViewModel::class.java)
        viewModel.personRepository = repository
        viewModel.scheduler = scheduler
        return viewModel
    }
}