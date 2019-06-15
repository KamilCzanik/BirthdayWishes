package com.example.birthdaywishes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.birthdaywishes.data.Person
import com.example.birthdaywishes.db.PeopleRepository
import com.example.birthdaywishes.ui.PeopleFragment
import javax.inject.Inject

class PeopleViewModel(application: Application) : AndroidViewModel(application),PeopleFragment.ViewModel {

    @Inject lateinit var repository: PeopleRepository
    override val allItems by lazy { repository.people }

    override fun delete(item: Person) { repository.delete(item) }
}