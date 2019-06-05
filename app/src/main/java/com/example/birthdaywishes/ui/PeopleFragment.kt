package com.example.birthdaywishes.ui


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.R
import com.example.birthdaywishes.adapter.PeopleAdapter
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.people.DaggerPeopleComponent
import com.example.birthdaywishes.di.people.PeopleModule
import com.example.birthdaywishes.pojo.Person
import kotlinx.android.synthetic.main.fragment_people.*
import javax.inject.Inject


class PeopleFragment : Fragment() {

    @Inject lateinit var viewModel: ViewModel
    @Inject lateinit var peopleAdapter: PeopleAdapter

    val onPersonItemClickListener = object : OnPersonItemClickListener {
        override fun onClick(pos: Int) {
            val person = peopleAdapter.getPersonAt(pos)
            val navAction = PeopleFragmentDirections.actionPeopleFragmentToPersonFragment(person)
            findNavController().navigate(navAction)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        configureRecycler()
        peopleFragment_fab.setOnClickListener { findNavController().navigate(R.id.action_peopleFragment_to_addPersonFragment)}
        viewModel.people.observe(this, Observer { peopleAdapter.submitList(it) })
    }

    private fun injectDependencies() {
        DaggerPeopleComponent
            .builder()
            .peopleModule(PeopleModule(activity!!.application,this))
            .daoModule(DaoModule(activity!!.application))
            .build()
            .inject(this)
    }

    private fun configureRecycler() {
        peopleFragment_recyclerView.layoutManager = LinearLayoutManager(context)
        peopleFragment_recyclerView.setHasFixedSize(true)
        peopleFragment_recyclerView.adapter = peopleAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
                showRemovePersonAlertDialog(peopleAdapter.getPersonAt(viewHolder.adapterPosition))


        }).attachToRecyclerView(peopleFragment_recyclerView)
    }


    fun showRemovePersonAlertDialog(person: Person) {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete)
            .setMessage(R.string.remove_person_question)
            .setPositiveButton(R.string.submit) { _,_ -> viewModel.delete(person)}
            .setNegativeButton(R.string.cancel) {dialog,_ -> dialog.cancel() }
            .setCancelable(true)
            .create().show()
    }

    interface OnPersonItemClickListener {
        fun onClick(pos: Int)
    }

    interface ViewModel {
        val people: LiveData<List<Person>>

        fun delete(person: Person)
    }
}
