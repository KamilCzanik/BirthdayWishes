package com.example.birthdaywishes.di.people

import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.ui.PeopleFragment
import dagger.Component

@Component(modules = [PeopleModule::class,DaoModule::class])
interface PeopleComponent {
    fun inject(peopleFragment: PeopleFragment)
}