package com.example.birthdaywishes.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.birthdaywishes.R
import com.example.birthdaywishes.application
import com.example.birthdaywishes.di.SystemServiceModule
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.personModification.editPerson.DaggerEditPersonComponent
import com.example.birthdaywishes.di.personModification.editPerson.EditPersonModule
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.setTitle
import com.example.birthdaywishes.ui.core.PersonModificationFragment
import javax.inject.Inject

class EditPersonFragment : PersonModificationFragment() {

    @Inject override lateinit var viewModel: ViewModel
    private val args: EditPersonFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        setPerson()
    }

    private fun setPerson() {
        viewModel.personToEdit = args.person
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        setTitle(R.string.edit_person)
    }

    private fun bindData() {
        personBinding.personItem = viewModel.personToEdit
        personBinding.executePendingBindings()
    }

    private fun injectDependencies() {
        DaggerEditPersonComponent.builder()
            .daoModule(DaoModule(application()))
            .editPersonModule(EditPersonModule(application()))
            .systemServiceModule(SystemServiceModule(context!!))
            .build()
            .inject(this)
    }

    override fun getPerson() = super.getPerson().apply { id = viewModel.personToEdit.id }

    interface ViewModel : PersonModificationFragment.ViewModel {
        var personToEdit : Person
    }
}