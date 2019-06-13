package com.example.birthdaywishes.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.*
import com.example.birthdaywishes.adapter.WishesAdapter
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.wishes.DaggerWishesComponent
import com.example.birthdaywishes.di.wishes.WishesModule
import com.example.birthdaywishes.pojo.Wishes
import com.example.birthdaywishes.ui.core.RecyclerViewFragment
import kotlinx.android.synthetic.main.fragment_wishes.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_wishes.fragmentWishesCancel_button as cancelButton
import kotlinx.android.synthetic.main.fragment_wishes.fragmentWishesSubmit_button as submitButton
import kotlinx.android.synthetic.main.fragment_wishes.wishesFragmentInputField_cardView as cardView
import kotlinx.android.synthetic.main.fragment_wishes.wishesFragmentInput_editText as wishesInput

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
        wishesFragment_fab.setOnClickListener { setWishesInputVisible(isInputVisible()) }
        cancelButton.setOnClickListener { setWishesInputVisible(false) }
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
    private fun isInputVisible() = cardView.visibility == View.GONE

    private fun setWishesInputVisible(setVisible: Boolean) {
        if(setVisible)
            cardView.appearAndSlideUp()
        else
            cardView.slideDownAndDisappear()
    }

    private fun validateDataInRealTime() {
        wishesInput.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                submitButton.isEnabled = getInsertedWishes().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
    }

    //endregion

    private fun addWishes() {
        viewModel.add(Wishes(getInsertedWishes()))
        setWishesInputVisible(false)
        clearInput()
    }

    private fun getInsertedWishes() = wishesInput.text.toString().trim()

    private fun clearInput() = wishesInput.setText("")

    interface ViewModel : RecyclerViewFragment.ViewModel<Wishes> {
        fun add(wishes: Wishes)
    }
}
