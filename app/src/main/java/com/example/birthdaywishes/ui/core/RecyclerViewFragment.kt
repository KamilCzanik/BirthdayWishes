package com.example.birthdaywishes.ui.core

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.R

abstract class RecyclerViewFragment : Fragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        configureRecycler()
        setOnClickListeners()
        setUpObservers()
    }

    abstract fun configureRecycler()

    abstract fun setOnClickListeners()

    abstract fun setUpObservers()

    abstract fun injectDependencies()

    protected fun basicConfiguration(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        ItemTouchHelper(getItemTouchHelperSimpleCallback()).attachToRecyclerView(recyclerView)
    }

    private fun getItemTouchHelperSimpleCallback() = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) { onItemSwipe(viewHolder) }

    }

    abstract fun onItemSwipe(viewHolder: RecyclerView.ViewHolder)

    fun <T> showRemovingDialog(item: T,onDialogCancel: () -> Any) {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete)
            .setMessage(R.string.remove_item_question)
            .setPositiveButton(R.string.submit) { _, _ -> deleteItem(item)}
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            .setCancelable(true)
            .setOnCancelListener { onDialogCancel() }
            .create()
            .show()
    }
    
    abstract fun <T> deleteItem(item: T)

    interface ViewModel<T> {
        val allItems: LiveData<List<T>>

        fun delete(wishes: T)
    }
}