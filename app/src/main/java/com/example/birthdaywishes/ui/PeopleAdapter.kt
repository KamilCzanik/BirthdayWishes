package com.example.birthdaywishes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.databinding.PersonItemBinding
import com.example.birthdaywishes.pojo.Person

class PeopleAdapter(private val people: List<Person>) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(PersonItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount() = people.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(people[position])


    class ViewHolder(private val binding: PersonItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            binding.personItem = person
            binding.executePendingBindings()
        }
    }
}