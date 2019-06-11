package com.example.birthdaywishes.di.personModification.addPerson

import com.example.birthdaywishes.di.SystemServiceModule
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.ui.AddPersonFragment
import dagger.Component

@Component(modules = [AddPersonModule::class,DaoModule::class, SystemServiceModule::class])
interface AddPersonComponent {
    fun inject(addPersonFragment: AddPersonFragment)
}