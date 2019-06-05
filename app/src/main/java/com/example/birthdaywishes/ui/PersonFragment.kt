package com.example.birthdaywishes.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.birthdaywishes.PermissionEvent
import com.example.birthdaywishes.PermissionNotGrantedEvent
import com.example.birthdaywishes.R
import com.example.birthdaywishes.adapter.SelectWishesAdapter
import com.example.birthdaywishes.databinding.FragmentPersonBinding
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.person.DaggerPersonComponent
import com.example.birthdaywishes.di.person.PersonModule
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.pojo.Wishes
import kotlinx.android.synthetic.main.fragment_person.*
import javax.inject.Inject

class PersonFragment : Fragment() {

    private lateinit var binding : FragmentPersonBinding
    private val args: PersonFragmentArgs by navArgs()

    @Inject lateinit var wishesAdapter : SelectWishesAdapter
    @Inject lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        injectDependencies()
        binding = FragmentPersonBinding.inflate(inflater,container,false)
        viewModel.currentPerson = args.person
        return binding.root
    }

    private fun injectDependencies() {
        DaggerPersonComponent.builder()
            .daoModule(DaoModule(activity!!.application))
            .personModule(PersonModule(activity as MainActivity))
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerWishesRecycler()
        configureButtons()
        bindPersonData()
        observeViewModel()
    }

    private fun configureRecyclerWishesRecycler() {
        personFragmentWishes_recyclerView.apply {
            adapter = wishesAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun configureButtons() {
        personFragmentSend_button.setOnClickListener { sendWishes() }
        personFragmentShare_button.setOnClickListener { shareWishes()  }
    }

    private fun sendWishes() {
        viewModel.sendWishes(wishesAdapter.getSelectedWishes())
    }

    private fun shareWishes() {
        viewModel.shareWishes(wishesAdapter.getSelectedWishes())
    }

    private fun bindPersonData() {
        binding.personItem = viewModel.currentPerson
        binding.executePendingBindings()
    }

    private fun observeViewModel() {
        viewModel.allWishes.observe(this, Observer { wishesAdapter.submitList(it) })
        viewModel.permissionEvent.observe(this, Observer { event ->
            if(event is PermissionNotGrantedEvent )
                Toast.makeText(context,R.string.permission_not_granted_toast,Toast.LENGTH_LONG).show()
            else
                Toast.makeText(context,R.string.sms_send,Toast.LENGTH_LONG).show()
        })
    }

    interface ViewModel {
        val allWishes: LiveData<List<Wishes>>
        val permissionEvent: LiveData<PermissionEvent>
        var currentPerson: Person

        fun sendWishes(wishes: Wishes)
        fun shareWishes(wishes: Wishes)
    }

}
