package com.example.birthdaywishes.ui


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.birthdaywishes.R
import com.example.birthdaywishes.application
import com.example.birthdaywishes.data.Wishes
import com.example.birthdaywishes.di.addWishes.AddWishesModule
import com.example.birthdaywishes.di.addWishes.DaggerAddWishesComponent
import com.example.birthdaywishes.di.dao.DaoModule
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_add_wishes.fragmentAddWishesInput_editText as wishesInput
import kotlinx.android.synthetic.main.fragment_add_wishes.fragmentAddWishesSubmit_button as submitButton

class AddWishesFragment : Fragment() {

    @Inject lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_wishes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        validateDataInRealTime()
        setOnClickListeners()
    }

    private fun injectDependencies() {
        DaggerAddWishesComponent.builder()
            .addWishesModule(AddWishesModule(application()))
            .daoModule(DaoModule(application()))
            .build()
            .inject(this)
    }

    private fun validateDataInRealTime() = wishesInput.addTextChangedListener(getTextChangeListener())

    private fun getTextChangeListener() = object : TextWatcher {

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            submitButton.isEnabled = getInsertedWishesContent().isNotEmpty()
        }
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    }

    private fun setOnClickListeners() = submitButton.setOnClickListener { addWishes() }

    private fun addWishes() {
        viewModel.add(getWishes())
        clearInput()
        dismissKeyboard()
    }

    private fun getWishes() = Wishes(getInsertedWishesContent())

    private fun getInsertedWishesContent() = wishesInput.text.toString().trim()

    private fun clearInput() = wishesInput.setText("")

    private fun dismissKeyboard() {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).
            hideSoftInputFromWindow(wishesInput.windowToken, 0)
    }

    interface ViewModel {
        fun add(wishes: Wishes)
    }
}
