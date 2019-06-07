package com.example.birthdaywishes.adapter

import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.birthdaywishes.R
import com.example.birthdaywishes.pojo.EmptyWishes
import com.example.birthdaywishes.pojo.Wishes
import javax.inject.Inject


class SelectWishesAdapter @Inject constructor() : WishesAdapter() {

    private var selectedWishesPos: Int = NO_POSITION

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

    fun getSelectedWishes(): Wishes = if(selectedWishesPos == NO_POSITION) EmptyWishes() else getItem(selectedWishesPos)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setBackgroundResource(
            if (holder.adapterPosition == selectedWishesPos)
                R.drawable.selected_background
            else
                Color.TRANSPARENT
        )
    }

}