package com.example.birthdaywishes.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.birthdaywishes.R
import com.example.birthdaywishes.adapter.PeopleAdapter
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.people.DaggerPeopleComponent
import com.example.birthdaywishes.di.people.PeopleModule
import com.example.birthdaywishes.pojo.Person
import javax.inject.Inject


class PeopleFragment : Fragment() {

    @Inject lateinit var viewModel: ViewModel
    @Inject lateinit var peopleAdapter: PeopleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        injectDependencies()

        viewModel.people.observe(this, Observer { peopleAdapter.submitList(it) })
        
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    private fun injectDependencies() {
        DaggerPeopleComponent
            .builder()
            .peopleModule(PeopleModule(activity!!.application))
            .daoModule(DaoModule(activity!!.application))
            .build()
            .inject(this)
    }

    interface ViewModel {
        val people: LiveData<List<Person>>
    }
}
