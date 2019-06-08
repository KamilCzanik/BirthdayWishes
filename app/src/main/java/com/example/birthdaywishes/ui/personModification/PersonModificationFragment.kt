package com.example.birthdaywishes.ui.personModification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.birthdaywishes.R
import com.example.birthdaywishes.databinding.FragmentEditPersonBinding
import com.example.birthdaywishes.event.InvalidPersonDataEvent
import com.example.birthdaywishes.event.PersonDataEvent
import com.example.birthdaywishes.pojo.Birthday
import com.example.birthdaywishes.pojo.DaysOfMonth
import com.example.birthdaywishes.pojo.Person
import kotlinx.android.synthetic.main.fragment_edit_person.*

abstract class PersonModificationFragment : Fragment() {

    protected lateinit var personBinding: FragmentEditPersonBinding
    abstract val viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        personBinding = FragmentEditPersonBinding.inflate(inflater,container,false)
        return personBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        configureNumberPickers()
        configureButtons()
    }

    private fun observeViewModel() {
        viewModel.personDataEvent.observe(this, Observer {dataEvent ->
            if (dataEvent is InvalidPersonDataEvent)
                Toast.makeText(context, R.string.invalid_person_data_toast, Toast.LENGTH_LONG).show()
            else
                Toast.makeText(context,"Person created", Toast.LENGTH_LONG).show()
            //TODO change to other fragment
        })
    }

    private fun configureNumberPickers() {
        setMinMaxPickersValues()
        setDaysValueController()
    }

    private fun setMinMaxPickersValues() {
        editPersonFragmentMonth_numberPicker.minValue = 1
        editPersonFragmentMonth_numberPicker.maxValue = 12
        editPersonFragmentDay_numberPicker.minValue = DaysOfMonth.MIN_DAYS
        editPersonFragmentDay_numberPicker.maxValue = DaysOfMonth[1]
    }

    private fun setDaysValueController() {
        editPersonFragmentMonth_numberPicker.setOnValueChangedListener { _, _, newMonth ->
            val monthDayCount = DaysOfMonth[newMonth]

            editPersonFragmentDay_numberPicker.maxValue = monthDayCount

            if(editPersonFragmentDay_numberPicker.value > monthDayCount)
                editPersonFragmentDay_numberPicker.value = monthDayCount
        }
    }

    protected open fun getPerson() = Person(
        editPersonFragmentName_editText.text.toString().trim(),
        getBirthday(),
        editPersonFragmentPhone_editText.text.toString().trim()
    )

    private fun getBirthday() = Birthday(
        editPersonFragmentDay_numberPicker.value,
        editPersonFragmentMonth_numberPicker.value
    )

    private fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener { activity?.onBackPressed() }
        editPersonFragmentSubmit_button.setOnClickListener { viewModel.save(getPerson()) }
    }

    interface ViewModel {
        val personDataEvent : LiveData<PersonDataEvent>
        fun save(person: Person)
    }
}