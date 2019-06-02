package com.example.birthdaywishes.ui.personModification


import com.example.birthdaywishes.pojo.Person
import kotlinx.android.synthetic.main.fragment_edit_person.*
import javax.inject.Inject

class AddPersonFragment : PersonModificationFragment() {

    @Inject override lateinit var viewModel: ViewModel

    override fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener { /*TODO change fragment*/ }
        editPersonFragmentSubmit_button.setOnClickListener { viewModel.add(getPerson()) }
    }

    interface ViewModel : PersonModificationFragment.ViewModel{
        fun add(person: Person)
    }
}
