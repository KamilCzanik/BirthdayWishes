package com.example.birthdaywishes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.birthdaywishes.InvalidPersonDataEvent
import com.example.birthdaywishes.PersonDataEvent
import com.example.birthdaywishes.ValidPersonDataEvent
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.repository.PersonRepository
import com.example.birthdaywishes.systemService.BirthdayAlarmScheduler
import com.example.birthdaywishes.ui.personModification.AddPersonFragment
import javax.inject.Inject

class AddPersonViewModel(application: Application) : AndroidViewModel(application),AddPersonFragment.ViewModel {

    override val personDataEvent = MutableLiveData<PersonDataEvent>()
    @Inject lateinit var personRepository: PersonRepository
    @Inject lateinit var scheduler: BirthdayAlarmScheduler

    override fun add(person: Person) {
        if(person.isDataValid()) {
            personDataEvent.value = ValidPersonDataEvent()
            personRepository.add(person) { id ->
                person.id = id
                scheduler.scheduleBirthdayAlarm(person)
            }
        } else
            personDataEvent.value = InvalidPersonDataEvent()
    }
}