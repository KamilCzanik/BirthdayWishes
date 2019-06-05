package com.example.birthdaywishes.viewmodel.personModification

import android.app.Application
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.repository.PersonRepository
import com.example.birthdaywishes.systemService.BirthdayAlarmScheduler
import com.example.birthdaywishes.ui.personModification.AddPersonFragment
import javax.inject.Inject

class AddPersonViewModel(application: Application) : PersonModificationViewModel(application),AddPersonFragment.ViewModel {

    @Inject lateinit var personRepository: PersonRepository
    @Inject lateinit var scheduler: BirthdayAlarmScheduler

    override fun saveToRepository(person: Person) {
        personRepository.add(person) { id ->
            person.id = id
            scheduler.scheduleBirthdayAlarm(person)
        }
    }
}