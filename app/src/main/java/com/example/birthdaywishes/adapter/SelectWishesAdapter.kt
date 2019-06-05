package com.example.birthdaywishes.adapter

import android.graphics.Color
import android.view.ViewGroup
import com.example.birthdaywishes.R
import javax.inject.Inject


class SelectWishesAdapter @Inject constructor() : WishesAdapter() {

    private var selectedWishesPos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = super.onCreateViewHolder(parent, viewType)
        viewHolder.itemView.setOnClickListener {
            val oldSelected = selectedWishesPos
            selectedWishesPos = viewHolder.adapterPosition
            notifyItemChanged(oldSelected)
            notifyItemChanged(selectedWishesPos)
        }

        return viewHolder
    }

    fun getSelectedWishes() = getItem(selectedWishesPos)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setBackgroundResource(
            if (holder.adapterPosition == selectedWishesPos)
                R.drawable.selected_recycler_item_background
            else
                Color.TRANSPARENT
        )
    }

}