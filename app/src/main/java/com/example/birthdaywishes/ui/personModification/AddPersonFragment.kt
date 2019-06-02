package com.example.birthdaywishes.ui.personModification


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.birthdaywishes.InvalidPersonDataEvent
import com.example.birthdaywishes.PersonDataEvent
import com.example.birthdaywishes.R
import com.example.birthdaywishes.pojo.Person
import kotlinx.android.synthetic.main.fragment_edit_person.*
import javax.inject.Inject

class AddPersonFragment : PersonModificationFragment() {

    @Inject lateinit var viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        configureButtons()
    }

    private fun observeViewModel() {
        viewModel.personDataEvent.observe(this, Observer {dataEvent ->
            if (dataEvent is InvalidPersonDataEvent)
                Toast.makeText(context, R.string.invalid_person_data_toast,Toast.LENGTH_LONG).show()
            else
                Toast.makeText(context,"Person created",Toast.LENGTH_LONG).show()
            //TODO change to other fragment
        })
    }

    private fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener {  }
        editPersonFragmentSubmit_button.setOnClickListener { viewModel.add(getPerson()) }
    }

    interface ViewModel {
        val personDataEvent : LiveData<PersonDataEvent>
        fun add(person: Person)
    }
}
