package com.example.birthdaywishes.viewmodel.personModification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.birthdaywishes.InvalidPersonDataEvent
import com.example.birthdaywishes.PersonDataEvent
import com.example.birthdaywishes.ValidPersonDataEvent
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.ui.personModification.PersonModificationFragment

abstract class PersonModificationViewModel(application: Application) : AndroidViewModel(application),PersonModificationFragment.ViewModel {

    override val personDataEvent = MutableLiveData<PersonDataEvent>()

    override fun save(person: Person) {
        if(person.isDataValid()) {
            validPersonData()
            saveToRepository(person)
        } else
            invalidPersonData()
    }

    private fun invalidPersonData() { personDataEvent.value = InvalidPersonDataEvent() }

    private fun validPersonData() { personDataEvent.value = ValidPersonDataEvent() }

    protected abstract fun saveToRepository(person: Person)
}