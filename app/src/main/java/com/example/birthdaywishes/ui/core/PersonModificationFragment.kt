package com.example.birthdaywishes.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.birthdaywishes.showLongToast
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
        viewModel.personDataEvent.observe(this, Observer {event -> handlePersonEvent(event)})
    }

    private  fun handlePersonEvent(event: PersonDataEvent) {
        when(event) {
            is InvalidPersonDataEvent ->  showLongToast(R.string.invalid_person_data_toast)
            else -> finishFragment()
        }
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

    private fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener { finishFragment() }
        editPersonFragmentSubmit_button.setOnClickListener { savePerson() }
    }

    private fun savePerson() = viewModel.save(getPerson())

    private fun finishFragment() { activity?.onBackPressed() }

    protected open fun getPerson() = Person( getName(), getBirthday(), getPhone())

    private fun getBirthday() = Birthday( getDay(), getMonth())

    private fun getDay() = editPersonFragmentDay_numberPicker.value

    private fun getMonth() = editPersonFragmentMonth_numberPicker.value

    private fun getName() = editPersonFragmentName_editText.text.toString().trim()

    private fun getPhone() = editPersonFragmentPhone_editText.text.toString().trim()

    interface ViewModel {
        val personDataEvent : LiveData<PersonDataEvent>
        fun save(person: Person)
    }
}