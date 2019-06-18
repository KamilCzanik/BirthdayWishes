package com.example.birthdaywishes.ui


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.*
import com.example.birthdaywishes.adapter.WishesAdapter
import com.example.birthdaywishes.data.Wishes
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.wishes.DaggerWishesComponent
import com.example.birthdaywishes.di.wishes.WishesModule
import com.example.birthdaywishes.ui.core.RecyclerViewFragment
import kotlinx.android.synthetic.main.fragment_wishes.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_wishes.fragmentWishesCancel_button as cancelButton
import kotlinx.android.synthetic.main.fragment_wishes.fragmentWishesSubmit_button as submitButton
import kotlinx.android.synthetic.main.fragment_wishes.wishesFragmentInputField_cardView as cardView
import kotlinx.android.synthetic.main.fragment_wishes.wishesFragmentInput_editText as wishesInput
import kotlinx.android.synthetic.main.fragment_wishes.wishesFragment_fab as fab


class WishesFragment : RecyclerViewFragment<Wishes>() {

    @Inject lateinit var viewModel: ViewModel
    @Inject lateinit var wishesAdapter: WishesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wishes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.wishes)
        validateDataInRealTime()
    }

    //region view configuration
    override fun injectDependencies() {
        DaggerWishesComponent.builder()
            .daoModule(DaoModule(application()))
            .wishesModule(WishesModule(application()))
            .build()
            .inject(this)
    }

    override fun configureRecycler() {
        basicConfiguration(wishesFragment_recyclerView)
        wishesFragment_recyclerView.adapter = wishesAdapter
    }

    override fun setUpObservers() {
        viewModel.allItems.observe(this, Observer { wishesAdapter.submitList(it) })
    }

    override fun setOnClickListeners() {
        fab.setOnClickListener { if(isInputVisible()) hideWishesInput() else showWishesInput() }
        cancelButton.setOnClickListener { hideWishesInput() }
        submitButton.setOnClickListener { addWishes() }
    }

    //endregion

    //region recycler actions impl
    override fun onItemSwipe(viewHolder: RecyclerView.ViewHolder) {
        showRemovingDialog(
            wishesAdapter.getWishesAt(viewHolder.adapterPosition)) {wishesAdapter.notifyDataSetChanged()}
    }

    override fun deleteItem(item: Wishes) { viewModel.delete(item) }

    //endregion

    //region wishesInput management
    private fun isInputVisible() = cardView.visibility == View.VISIBLE

    private fun hideWishesInput() {
        cardView.slideDownAndDisappear()
        dismissKeyboard()
    }

    private fun showWishesInput() = cardView.appearAndSlideUp()

    private fun dismissKeyboard() {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).
        hideSoftInputFromWindow(wishesInput.windowToken, 0)
    }

    private fun validateDataInRealTime() {
        wishesInput.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                submitButton.isEnabled = getInsertedWishesContent().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
    }

    //endregion

    private fun addWishes() {
        viewModel.add(getWishes())
        hideWishesInput()
        clearInput()
    }

    private fun getWishes() = Wishes(getInsertedWishesContent())

    private fun getInsertedWishesContent() = wishesInput.text.toString().trim()

    private fun clearInput() = wishesInput.setText("")

    interface ViewModel : RecyclerViewFragment.ViewModel<Wishes> {
        fun add(wishes: Wishes)
    }
}
