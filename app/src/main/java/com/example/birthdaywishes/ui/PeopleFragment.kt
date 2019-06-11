package com.example.birthdaywishes.ui


import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.R
import com.example.birthdaywishes.adapter.PeopleAdapter
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.people.DaggerPeopleComponent
import com.example.birthdaywishes.di.people.PeopleModule
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.ui.core.RecyclerViewFragment
import kotlinx.android.synthetic.main.fragment_people.*
import javax.inject.Inject


class PeopleFragment : RecyclerViewFragment() {

    @Inject lateinit var viewModel: ViewModel
    @Inject lateinit var peopleAdapter: PeopleAdapter

    val onPersonItemClickListener = object : OnPersonItemClickListener {
        override fun onClick(pos: Int) { navigateToPersonFragment(peopleAdapter.getPersonAt(pos))}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    //region view configuration
    override fun configureRecycler() {
        basicConfiguration(peopleFragment_recyclerView)
        peopleFragment_recyclerView.adapter = peopleAdapter
    }

    override fun setOnClickListeners() {
        peopleFragment_fab.setOnClickListener { findNavController().navigate(R.id.action_peopleFragment_to_addPersonFragment)}
    }

    override fun setUpObservers() {
        viewModel.allItems.observe(this, Observer { peopleAdapter.submitList(it) })
    }

    override fun injectDependencies() {
        DaggerPeopleComponent
            .builder()
            .peopleModule(PeopleModule(activity!!.application,this))
            .daoModule(DaoModule(activity!!.application))
            .build()
            .inject(this)
    }

    //endregion

    //region recycler actions impl
    override fun onItemSwipe(viewHolder: RecyclerView.ViewHolder) {
        showRemovingDialog(
            peopleAdapter.getPersonAt(viewHolder.adapterPosition),
            {peopleAdapter.notifyDataSetChanged()})
    }

    override fun <T> deleteItem(item: T) { viewModel.delete(item as Person) }

    //endregion

    //region options menu config
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.people_fragment_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_wishes) goToWishesFragment()
        return super.onOptionsItemSelected(item)
    }

    //endregion

    //region navigation
    private fun goToWishesFragment() {
        val navAction = PeopleFragmentDirections.actionPeopleFragmentToWishesFragment()
        findNavController().navigate(navAction)
    }

    private fun navigateToPersonFragment(person: Person) {
        val navAction = PeopleFragmentDirections.actionPeopleFragmentToPersonFragment(person)
        findNavController().navigate(navAction)
    }

    //endregion

    interface OnPersonItemClickListener { fun onClick(pos: Int) }

    interface ViewModel : RecyclerViewFragment.ViewModel<Person>
}
