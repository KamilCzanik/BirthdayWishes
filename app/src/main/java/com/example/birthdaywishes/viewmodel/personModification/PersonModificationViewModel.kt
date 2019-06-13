package com.example.birthdaywishes.viewmodel.personModification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.ui.core.PersonModificationFragment

abstract class PersonModificationViewModel(application: Application) : AndroidViewModel(application),
    PersonModificationFragment.ViewModel {

    override fun save(person: Person) { if(person.isDataValid()) saveToRepository(person) }

    protected abstract fun saveToRepository(person: Person)
}