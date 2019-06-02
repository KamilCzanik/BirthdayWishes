package com.example.birthdaywishes.ui.personModification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.birthdaywishes.R
import com.example.birthdaywishes.pojo.DaysOfMonth
import kotlinx.android.synthetic.main.fragment_edit_person.*

abstract class PersonModificationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNumberPickers()
    }

    private fun configureNumberPickers() {
        setMinMaxPickersValues()
        setDaysValueController()
    }

    private fun setMinMaxPickersValues() {
        editPersonFragmentMonth_numberPicker.minValue = 1
        editPersonFragmentMonth_numberPicker.minValue = 12
        editPersonFragmentDay_numberPicker.minValue = DaysOfMonth.MIN_DAYS
        editPersonFragmentDay_numberPicker.maxValue = DaysOfMonth[1]
    }

    private fun setDaysValueController() {
        editPersonFragmentMonth_numberPicker.setOnValueChangedListener { _, _, newMonth ->
            val monthDayCount = DaysOfMonth[newMonth]

            if(editPersonFragmentDay_numberPicker.value > monthDayCount) {
                editPersonFragmentDay_numberPicker.maxValue = monthDayCount
                editPersonFragmentDay_numberPicker.value = monthDayCount
            }
        }
    }
}