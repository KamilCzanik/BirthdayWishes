package com.example.birthdaywishes.ui.personModification

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.personModification.editPerson.DaggerEditPersonComponent
import com.example.birthdaywishes.di.personModification.editPerson.EditPersonModule
import com.example.birthdaywishes.pojo.Person
import kotlinx.android.synthetic.main.fragment_edit_person.*
import javax.inject.Inject

class EditPersonFragment : PersonModificationFragment() {

    @Inject override lateinit var viewModel: ViewModel
    private val args: EditPersonFragmentArgs by navArgs()
    private val personToEdit by lazy { args.person }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        personBinding.personItem = personToEdit
        personBinding.executePendingBindings()
    }

    private fun injectDependencies() {
        DaggerEditPersonComponent.builder()
            .daoModule(DaoModule(activity!!.application))
            .editPersonModule(EditPersonModule(activity!!.application))
            .build()
            .inject(this)
    }

    override fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener { activity?.onBackPressed() }
        editPersonFragmentSubmit_button.setOnClickListener { viewModel.update(personToEdit,getPerson()) } }

    override fun getPerson() = super.getPerson().apply { id = personToEdit.id }

    interface ViewModel : PersonModificationFragment.ViewModel{
        fun update(oldPerson: Person,newPerson: Person)
    }
}