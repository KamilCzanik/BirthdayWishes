package com.example.birthdaywishes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.data.Person
import com.example.birthdaywishes.databinding.PersonItemBinding
import com.example.birthdaywishes.getDrawableFor
import com.example.birthdaywishes.ui.PeopleFragment
import kotlinx.android.synthetic.main.person_item.view.*
import javax.inject.Inject

class PeopleAdapter @Inject constructor(
    private val onCLickListener: PeopleFragment.OnPersonItemClickListener) : ListAdapter<Person, PeopleAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(PersonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),onCLickListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))


    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Person>() {

            override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
        }
    }

    fun getPersonAt(pos: Int): Person = getItem(pos)

    class ViewHolder(
        private val binding: PersonItemBinding,
        private val onCLickListener: PeopleFragment.OnPersonItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        private val personImage: ImageView by lazy { binding.root.personItemImage_imageView }

        fun bind(person: Person) {
            binding.personItem = person
            binding.executePendingBindings()
            binding.root.setOnClickListener { onCLickListener.onClick(adapterPosition) }
            personImage.setImageDrawable(getDrawableFor(person))
        }
    }
}