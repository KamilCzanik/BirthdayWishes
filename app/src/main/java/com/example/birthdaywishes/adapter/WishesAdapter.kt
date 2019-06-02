package com.example.birthdaywishes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.databinding.WishesItemBinding
import com.example.birthdaywishes.pojo.Wishes

class WishesAdapter : ListAdapter<Wishes, WishesAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(WishesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    fun getWishesAt(pos: Int) : Wishes = getItem(pos)

    class ViewHolder(private val binding: WishesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wishes: Wishes) {
            binding.wishesItem = wishes
            binding.executePendingBindings()
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Wishes>() {

            override fun areItemsTheSame(oldItem: Wishes, newItem: Wishes) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Wishes, newItem: Wishes) = oldItem.content == newItem.content
        }
    }
}