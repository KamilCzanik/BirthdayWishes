package com.example.birthdaywishes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.databinding.PersonItemBinding
import com.example.birthdaywishes.pojo.Person
import javax.inject.Inject

class PeopleAdapter @Inject constructor() : ListAdapter<Person,PeopleAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(PersonItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))


    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Person>() {

            override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
        }
    }

    class ViewHolder(private val binding: PersonItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.personItem = person
            binding.executePendingBindings()
        }
    }
}