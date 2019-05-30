package com.example.birthdaywishes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.birthdaywishes.POJO.Person
import com.example.birthdaywishes.repository.PeopleRepository
import javax.inject.Inject

class PeopleViewModel(application: Application) : AndroidViewModel(application) {

    @Inject lateinit var repository: PeopleRepository
    val people by lazy { repository.people }

    fun delete(person: Person) { repository.delete(person) }
}