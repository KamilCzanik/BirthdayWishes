package com.example.birthdaywishes.ui.personModification

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.birthdaywishes.pojo.Person
import kotlinx.android.synthetic.main.fragment_edit_person.*

class EditPersonFragment : PersonModificationFragment() {

    /*TODO @Inject*/ override lateinit var viewModel: ViewModel
    private val args: EditPersonFragmentArgs by navArgs()
    private val personToEdit by lazy { args.person }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        personBinding.personItem = personToEdit
        personBinding.executePendingBindings()
    }

    override fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener { activity?.onBackPressed() }
        editPersonFragmentSubmit_button.setOnClickListener { viewModel.update(getPerson()) } }

    override fun getPerson() = super.getPerson().apply { id = personToEdit.id }

    interface ViewModel : PersonModificationFragment.ViewModel{
        fun update(person: Person)
    }
}