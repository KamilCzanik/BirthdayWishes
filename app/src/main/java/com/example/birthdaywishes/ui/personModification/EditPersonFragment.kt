package com.example.birthdaywishes.ui.personModification

import android.os.Bundle
import android.view.View
import com.example.birthdaywishes.pojo.Person
import kotlinx.android.synthetic.main.fragment_edit_person.*
import javax.inject.Inject

class EditPersonFragment : PersonModificationFragment() {

    @Inject override lateinit var viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO get person from action
        //TODO personBinding.personItem = person
        personBinding.executePendingBindings()
    }

    override fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener { /*TODO change fragment*/ }
        editPersonFragmentSubmit_button.setOnClickListener { viewModel.update(getPerson()) } }

    interface ViewModel : PersonModificationFragment.ViewModel{
        fun update(person: Person)
    }
}