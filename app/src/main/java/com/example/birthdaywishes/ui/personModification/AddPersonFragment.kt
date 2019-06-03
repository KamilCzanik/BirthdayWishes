package com.example.birthdaywishes.ui.personModification


import android.os.Bundle
import android.view.View
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.personModification.addPerson.AddPersonModule
import com.example.birthdaywishes.di.personModification.addPerson.DaggerAddPersonComponent
import com.example.birthdaywishes.pojo.Person
import kotlinx.android.synthetic.main.fragment_edit_person.*
import javax.inject.Inject

class AddPersonFragment : PersonModificationFragment() {

    @Inject override lateinit var viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        injectDependencies()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener { activity?.onBackPressed() }
        editPersonFragmentSubmit_button.setOnClickListener { viewModel.add(getPerson()) }
    }

    fun injectDependencies() {
        DaggerAddPersonComponent.builder()
            .addPersonModule(AddPersonModule(activity!!.application))
            .daoModule(DaoModule(activity!!.application))
            .build()
            .inject(this)
    }

    interface ViewModel : PersonModificationFragment.ViewModel{
        fun add(person: Person)
    }
}
