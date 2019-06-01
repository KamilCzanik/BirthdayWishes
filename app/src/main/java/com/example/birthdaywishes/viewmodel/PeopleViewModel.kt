package com.example.birthdaywishes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.repository.PeopleRepository
import com.example.birthdaywishes.ui.PeopleFragment
import javax.inject.Inject

class PeopleViewModel(application: Application) : AndroidViewModel(application),PeopleFragment.ViewModel {

    @Inject lateinit var repository: PeopleRepository
    override val people by lazy { repository.people }

    fun delete(person: Person) { repository.delete(person) }
}