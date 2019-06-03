package com.example.birthdaywishes.di.personModification.editPerson

import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.ui.personModification.EditPersonFragment
import dagger.Component

@Component(modules = [EditPersonModule::class,DaoModule::class])
interface EditPersonComponent {
    fun inject(editPersonFragment: EditPersonFragment)
}