package com.example.birthdaywishes.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.*
import com.example.birthdaywishes.adapter.WishesAdapter
import com.example.birthdaywishes.data.Wishes
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.wishes.DaggerWishesComponent
import com.example.birthdaywishes.di.wishes.WishesModule
import com.example.birthdaywishes.ui.core.RecyclerViewFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_wishes.wishesFragmentCancel_button as cancelButton
import kotlinx.android.synthetic.main.fragment_wishes.wishesFragmentInputField_cardView as cardView
import kotlinx.android.synthetic.main.fragment_wishes.wishesFragment_fab as fab
import kotlinx.android.synthetic.main.fragment_wishes.wishesFragment_recyclerView as recycler


class WishesFragment : RecyclerViewFragment<Wishes>() {

    @Inject lateinit var viewModel: ViewModel
    @Inject lateinit var wishesAdapter: WishesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wishes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.wishes)
    }

    //region view configuration
    override fun injectDependencies() {
        DaggerWishesComponent.builder()
            .daoModule(DaoModule(application()))
            .wishesModule(WishesModule(application()))
            .build()
            .inject(this)
    }

    override fun configureRecycler() = basicConfiguration(recycler.apply { adapter = wishesAdapter })

    override fun setUpObservers() = viewModel.allItems.observe(this, Observer { wishesAdapter.submitList(it) })

    override fun setOnClickListeners() {
        fab.setOnClickListener { showWishesInput() }
        cancelButton.setOnClickListener { hideWishesInput()}
    }

    private fun hideWishesInput() = cardView.slideDownAndDisappear()

    private fun showWishesInput() = cardView.appearAndSlideUp()
    //endregion

    //region recycler actions impl
    override fun onItemSwipe(viewHolder: RecyclerView.ViewHolder) =
        showRemovingDialog(wishesAdapter.getWishesAt(viewHolder.adapterPosition)) {wishesAdapter.notifyDataSetChanged()}

    override fun deleteItem(item: Wishes) = viewModel.delete(item)
    //endregion

    interface ViewModel : RecyclerViewFragment.ViewModel<Wishes>
}
