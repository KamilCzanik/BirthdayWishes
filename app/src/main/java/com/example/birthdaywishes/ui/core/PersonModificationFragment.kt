package com.example.birthdaywishes.ui.core

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.example.birthdaywishes.data.Birthday
import com.example.birthdaywishes.data.DaysInMonth
import com.example.birthdaywishes.data.Person
import com.example.birthdaywishes.databinding.FragmentEditPersonBinding
import com.example.birthdaywishes.finishFragment
import kotlinx.android.synthetic.main.fragment_edit_person.*
import kotlinx.android.synthetic.main.fragment_edit_person.editPersonFragmentDay_numberPicker as dayPicker
import kotlinx.android.synthetic.main.fragment_edit_person.editPersonFragmentMonth_numberPicker as monthPicker
import kotlinx.android.synthetic.main.fragment_edit_person.editPersonFragmentName_editText as nameInput
import kotlinx.android.synthetic.main.fragment_edit_person.editPersonFragmentPhone_editText as phoneInput
import kotlinx.android.synthetic.main.fragment_edit_person.editPersonFragmentSubmit_button as saveButton


abstract class PersonModificationFragment : Fragment() {

    protected lateinit var personBinding: FragmentEditPersonBinding
    abstract val viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        personBinding = FragmentEditPersonBinding.inflate(inflater,container,false)
        injectDependencies()
        return personBinding.root
    }

    abstract fun injectDependencies()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNumberPickers()
        configureButtons()
        validateDataInRealTime()
    }

    private fun validateDataInRealTime() {
        nameInput.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                saveButton.isEnabled = getName().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })
    }

    private fun configureNumberPickers() {
        setMinMaxPickersValues()
        setDaysValueController()
    }

    private fun setMinMaxPickersValues() {
        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        dayPicker.minValue = DaysInMonth.MIN_DAYS
        dayPicker.maxValue = DaysInMonth[1]
    }

    private fun setDaysValueController() {
        monthPicker.setOnValueChangedListener(getDaysValueController())
    }

    private fun getDaysValueController() = NumberPicker.OnValueChangeListener { _, _, newMonth ->
        val daysInMonth = DaysInMonth[newMonth]
        dayPicker.maxValue = daysInMonth
        if(getDay() > daysInMonth) setDay(daysInMonth)
    }

    private fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener { finishFragment() }
        saveButton.setOnClickListener { savePerson() }
    }

    private fun savePerson() {
        viewModel.save(getPerson())
        finishFragment()
    }

    protected open fun getPerson() = Person( getName(), getBirthday(), getPhone())

    private fun getDay() = dayPicker.value

    private fun setDay(day: Int) { dayPicker.value = day }

    private fun getMonth() = monthPicker.value

    private fun getBirthday() = Birthday( getDay(), getMonth())

    private fun getName() = nameInput.text.toString().trim()

    private fun getPhone() = phoneInput.text.toString().trim()

    interface ViewModel {
        fun save(person: Person)
    }
}