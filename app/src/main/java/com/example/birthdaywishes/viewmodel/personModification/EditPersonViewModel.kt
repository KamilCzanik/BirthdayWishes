package com.example.birthdaywishes.viewmodel.personModification

import android.app.Application
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.repository.PersonRepository
import com.example.birthdaywishes.systemService.BirthdayAlarmScheduler
import com.example.birthdaywishes.ui.personModification.EditPersonFragment
import javax.inject.Inject

class EditPersonViewModel(application: Application) : PersonModificationViewModel(application),EditPersonFragment.ViewModel {

    @Inject lateinit var personRepository: PersonRepository
    @Inject lateinit var scheduler: BirthdayAlarmScheduler

    override fun saveToRepository(person: Person) {
        personRepository.update(person)
        scheduler.scheduleBirthdayAlarm(person)
    }
}