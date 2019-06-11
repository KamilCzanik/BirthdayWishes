package com.example.birthdaywishes.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.R
import com.example.birthdaywishes.adapter.WishesAdapter
import com.example.birthdaywishes.appearAndSlideUp
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.wishes.DaggerWishesComponent
import com.example.birthdaywishes.di.wishes.WishesModule
import com.example.birthdaywishes.pojo.Wishes
import com.example.birthdaywishes.showLongToast
import com.example.birthdaywishes.slideDownAndDisappear
import com.example.birthdaywishes.ui.core.RecyclerViewFragment
import kotlinx.android.synthetic.main.fragment_wishes.*
import javax.inject.Inject

class WishesFragment : RecyclerViewFragment<Wishes>() {

    @Inject lateinit var viewModel: ViewModel
    @Inject lateinit var wishesAdapter: WishesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wishes, container, false)
    }

    //region view configuration
    override fun injectDependencies() {
        DaggerWishesComponent.builder()
            .daoModule(DaoModule(activity!!.application))
            .wishesModule(WishesModule(activity!!.application))
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
        fragmentWishesCancel_button.setOnClickListener { setWishesInputVisible(false) }
        fragmentWishesSubmit_button.setOnClickListener { addWishes() }
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
    private fun isInputVisible() = wishesFragmentInputField_cardView.visibility == View.GONE

    private fun setWishesInputVisible(setVisible: Boolean) {
        if(setVisible)
            wishesFragmentInputField_cardView.appearAndSlideUp()
        else
            wishesFragmentInputField_cardView.slideDownAndDisappear()
    }

    //endregion

    private fun addWishes() {
        val wishes = Wishes(wishesFragmentInput_editText.text.toString().trim())
        if(wishes.content.isNotEmpty()) {
            viewModel.add(wishes)
            setWishesInputVisible(false)
        } else
            showLongToast(R.string.insert_wishes_content_toast)
    }

    interface ViewModel : RecyclerViewFragment.ViewModel<Wishes> {
        fun add(wishes: Wishes)
    }
}
