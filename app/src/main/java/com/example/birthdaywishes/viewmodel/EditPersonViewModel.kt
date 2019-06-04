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
import com.example.birthdaywishes.ui.personModification.EditPersonFragment
import javax.inject.Inject

class EditPersonViewModel(application: Application) : AndroidViewModel(application),EditPersonFragment.ViewModel {

    override val personDataEvent = MutableLiveData<PersonDataEvent>()
    @Inject lateinit var personRepository: PersonRepository
    @Inject lateinit var scheduler: BirthdayAlarmScheduler

    override fun update(person: Person) {
        if(person.isDataValid()) {
            personDataEvent.value = ValidPersonDataEvent()
            personRepository.update(person)
            scheduler.scheduleBirthdayAlarm(person)
        } else
            personDataEvent.value = InvalidPersonDataEvent()
    }
}