package com.example.birthdaywishes.ui


import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaywishes.R
import com.example.birthdaywishes.adapter.PeopleAdapter
import com.example.birthdaywishes.application
import com.example.birthdaywishes.data.Person
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.people.DaggerPeopleComponent
import com.example.birthdaywishes.di.people.PeopleModule
import com.example.birthdaywishes.setTitle
import com.example.birthdaywishes.ui.core.RecyclerViewFragment
import kotlinx.android.synthetic.main.fragment_people.*
import javax.inject.Inject


class PeopleFragment : RecyclerViewFragment<Person>() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.people)
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
        viewModel.allItems.observe(this, Observer { items -> peopleAdapter.submitList(items) })
    }

    override fun injectDependencies() {
        DaggerPeopleComponent
            .builder()
            .peopleModule(PeopleModule(application(),this))
            .daoModule(DaoModule(application()))
            .build()
            .inject(this)
    }

    //endregion

    //region recycler actions impl
    override fun onItemSwipe(viewHolder: RecyclerView.ViewHolder) {
        showRemovingDialog(
            peopleAdapter.getPersonAt(viewHolder.adapterPosition)) {peopleAdapter.notifyDataSetChanged()}
    }

    override fun deleteItem(item: Person) { viewModel.delete(item) }

    //endregion

    //region options menu config
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.people_fragment_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_wishes) navigateToWishesFragment()
        return super.onOptionsItemSelected(item)
    }

    //endregion

    //region navigation
    private fun navigateToWishesFragment() {
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
