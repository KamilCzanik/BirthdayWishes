package com.example.birthdaywishes.adapter

import android.graphics.Color
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.birthdaywishes.R
import com.example.birthdaywishes.data.Wishes
import javax.inject.Inject


class SelectWishesAdapter @Inject constructor() : WishesAdapter() {

    private var selectedWishesPos: Int = NO_POSITION
    val isAnyItemSelected = MutableLiveData<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        super.onCreateViewHolder(parent, viewType).apply {
            itemView.setOnClickListener { setNewSelected(selectedWishesPos,adapterPosition) }
        }

    private fun setNewSelected(oldPos: Int, newPos: Int) {
        selectedWishesPos = newPos
        notifySelectedChange(oldPos,newPos)
    }

    private fun notifySelectedChange(oldPos: Int, newPos: Int) {
        notifyItemChanged(oldPos)
        notifyItemChanged(newPos)
        isAnyItemSelected.value = true
    }

    fun getSelectedWishes(): Wishes = getItem(selectedWishesPos)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setBackgroundResource(if(isSelected(holder)) R.drawable.selected_background else Color.TRANSPARENT)
    }

    fun isSelected(holder: ViewHolder) = holder.adapterPosition == selectedWishesPos
}