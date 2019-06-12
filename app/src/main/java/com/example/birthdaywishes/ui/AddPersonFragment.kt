package com.example.birthdaywishes.ui


import android.os.Bundle
import android.view.View
import com.example.birthdaywishes.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle(R.string.add_person)
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
