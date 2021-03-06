package com.example.birthdaywishes.viewmodel.personModification

import android.app.Application
import com.example.birthdaywishes.data.Person
import com.example.birthdaywishes.repository.PersonRepository
import com.example.birthdaywishes.system.BirthdayAlarmScheduler
import com.example.birthdaywishes.ui.EditPersonFragment
import javax.inject.Inject

class EditPersonViewModel(application: Application) : PersonModificationViewModel(application),
    EditPersonFragment.ViewModel {

    @Inject lateinit var personRepository: PersonRepository
    @Inject lateinit var scheduler: BirthdayAlarmScheduler
    override lateinit var personToEdit: Person

    override fun saveToRepository(person: Person) {
        setIdToUpdate(person)
        update(person)
    }

    private fun setIdToUpdate(person: Person) { person.id = personToEdit.id }

    private fun update(person: Person) {
        personRepository.update(person)
        scheduler.scheduleBirthdayAlarm(person)
    }
}