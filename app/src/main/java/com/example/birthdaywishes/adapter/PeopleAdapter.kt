package com.example.birthdaywishes.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.birthdaywishes.databinding.PersonItemBinding
import com.example.birthdaywishes.firstLetter
import com.example.birthdaywishes.pojo.Person
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
        private val colorGenerator: ColorGenerator = ColorGenerator.MATERIAL

        fun bind(person: Person) {
            binding.personItem = person
            binding.executePendingBindings()
            binding.root.setOnClickListener { onCLickListener.onClick(adapterPosition) }
            personImage.setImageDrawable(getTextDrawable(person))
        }

        private fun getTextDrawable(person: Person) : Drawable = TextDrawable.builder().buildRound(getLetter(person),getColor(getLetter(person)))

        private fun getColor(letter: String) = colorGenerator.getColor(letter)

        private fun getLetter(person: Person) = person.name.firstLetter()
    }
}