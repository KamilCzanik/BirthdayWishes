package com.example.birthdaywishes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.birthdaywishes.InvalidPersonDataEvent
import com.example.birthdaywishes.PersonDataEvent
import com.example.birthdaywishes.ValidPersonDataEvent
import com.example.birthdaywishes.pojo.Birthday
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.repository.PersonRepository
import com.example.birthdaywishes.ui.personModification.AddPersonFragment
import javax.inject.Inject

class AddPersonViewModel(application: Application) : AndroidViewModel(application),AddPersonFragment.ViewModel {

    override val personDataEvent = MutableLiveData<PersonDataEvent>()
    @Inject lateinit var personRepository: PersonRepository

    override fun add(person: Person) {
        if(isPersonDataValid(person)) {
            personDataEvent.value = ValidPersonDataEvent()
            personRepository.add(person)
        } else
            personDataEvent.value = InvalidPersonDataEvent()
    }

    private fun isPersonDataValid(person: Person) =
        person.name.isNotEmpty() &&
                person.phoneNumber.isNotEmpty() &&
                Birthday.isDateValid(person.birthday.day,person.birthday.month)
}