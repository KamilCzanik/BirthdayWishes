package com.example.birthdaywishes.ui


import android.os.Bundle
import com.example.birthdaywishes.application
import com.example.birthdaywishes.di.SystemServiceModule
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.personModification.addPerson.AddPersonModule
import com.example.birthdaywishes.di.personModification.addPerson.DaggerAddPersonComponent
import com.example.birthdaywishes.ui.core.PersonModificationFragment
import javax.inject.Inject

class AddPersonFragment : PersonModificationFragment() {

    @Inject override lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    private fun injectDependencies() {
        DaggerAddPersonComponent.builder()
            .addPersonModule(AddPersonModule(application()))
            .daoModule(DaoModule(application()))
            .systemServiceModule(SystemServiceModule(context!!))
            .build()
            .inject(this)
    }

    interface ViewModel : PersonModificationFragment.ViewModel
}
