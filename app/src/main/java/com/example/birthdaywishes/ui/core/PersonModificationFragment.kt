package com.example.birthdaywishes.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.birthdaywishes.R
import com.example.birthdaywishes.databinding.FragmentEditPersonBinding
import com.example.birthdaywishes.event.InvalidPersonDataEvent
import com.example.birthdaywishes.event.PersonDataEvent
import com.example.birthdaywishes.pojo.Birthday
import com.example.birthdaywishes.pojo.DaysInMonth
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.showLongToast
import kotlinx.android.synthetic.main.fragment_edit_person.*
import kotlinx.android.synthetic.main.fragment_edit_person.editPersonFragmentDay_numberPicker as dayPicker
import kotlinx.android.synthetic.main.fragment_edit_person.editPersonFragmentMonth_numberPicker as monthPicker

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
        if(day > daysInMonth) day = daysInMonth
    }

    private fun configureButtons() {
        editPersonFragmentCancel_button.setOnClickListener { finishFragment() }
        editPersonFragmentSubmit_button.setOnClickListener { savePerson() }
    }

    private fun savePerson() = viewModel.save(getPerson())

    private fun finishFragment() { activity?.onBackPressed() }

    protected open fun getPerson() = Person( name, birthday, phone)

    private var day: Int
    get() = dayPicker.value
    set(value) { dayPicker.value = value}

    private val month = monthPicker.value

    private val birthday = Birthday( day, month)

    private val name = editPersonFragmentName_editText.text.toString().trim()

    private val phone = editPersonFragmentPhone_editText.text.toString().trim()

    interface ViewModel {
        val personDataEvent : LiveData<PersonDataEvent>
        fun save(person: Person)
    }
}