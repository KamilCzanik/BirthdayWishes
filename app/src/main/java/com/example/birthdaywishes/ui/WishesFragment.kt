package com.example.birthdaywishes.ui


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.R
import com.example.birthdaywishes.adapter.WishesAdapter
import com.example.birthdaywishes.appearAndSlideUp
import com.example.birthdaywishes.pojo.Wishes
import com.example.birthdaywishes.slideDownAndDisappear
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.android.synthetic.main.fragment_wishes.*
import javax.inject.Inject

class WishesFragment : Fragment() {

    @Inject lateinit var viewModel: ViewModel
    @Inject lateinit var wishesAdapter: WishesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wishes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecycler()
        setOnClickListeners()
        setUpObservers()
    }

    private fun configureRecycler() {
        wishesFragment_recyclerView.layoutManager = LinearLayoutManager(context)
        wishesFragment_recyclerView.setHasFixedSize(true)
        wishesFragment_recyclerView.adapter = wishesAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
                showRemoveWishesAlertDialog(wishesAdapter.getWishesAt(viewHolder.adapterPosition))


        }).attachToRecyclerView(peopleFragment_recyclerView)
    }

    private fun setOnClickListeners() {
        wishesFragment_fab.setOnClickListener { wishesFragmentInputField_cardView.appearAndSlideUp() }
        fragmentWishesCancel_button.setOnClickListener { wishesFragmentInputField_cardView.slideDownAndDisappear() }
        fragmentWishesSubmit_button.setOnClickListener { addWishes() }
    }

    private fun setUpObservers() {
        viewModel.wishes.observe(this, Observer { wishesAdapter.submitList(it) })
    }

    private fun addWishes() {
        val wishes = Wishes(wishesFragmentInput_editText.text.toString().trim())
        if(wishes.content.isNotEmpty()) {
            viewModel.add(wishes)
            wishesFragmentInputField_cardView.slideDownAndDisappear()
        }
        else
            Toast.makeText(context,R.string.insert_wishes_content_toast,Toast.LENGTH_LONG).show()
    }

    fun showRemoveWishesAlertDialog(wishes: Wishes) {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete)
            .setMessage(R.string.remove_wishes_question)
            .setPositiveButton(R.string.submit) { _,_ -> viewModel.delete(wishes)}
            .setNegativeButton(R.string.cancel) {dialog,_ -> dialog.cancel() }
            .setCancelable(true)
            .create().show()
    }

    interface ViewModel {
        val wishes: LiveData<List<Wishes>>

        fun add(wishes: Wishes)
        fun delete(wishes: Wishes)
    }
}
