package com.example.birthdaywishes.di.person

import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.ui.PersonFragment
import dagger.Component

@Component(modules = [PersonModule::class,DaoModule::class])
interface PersonComponent {
    fun inject(fragment: PersonFragment)
}