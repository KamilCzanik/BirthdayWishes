package com.example.birthdaywishes.ui


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.birthdaywishes.R
import com.example.birthdaywishes.adapter.SelectWishesAdapter
import com.example.birthdaywishes.application
import com.example.birthdaywishes.data.Person
import com.example.birthdaywishes.data.Wishes
import com.example.birthdaywishes.databinding.FragmentPersonBinding
import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.di.person.DaggerPersonComponent
import com.example.birthdaywishes.di.person.PersonModule
import com.example.birthdaywishes.mainActivity
import com.example.birthdaywishes.setTitle
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_person.personFragmentSend_button as sendButton
import kotlinx.android.synthetic.main.fragment_person.personFragmentShare_button as shareButton
import kotlinx.android.synthetic.main.fragment_person.personFragmentWishes_recyclerView as wishesRecycler

class PersonFragment : Fragment() {

    private lateinit var binding : FragmentPersonBinding
    private val args: PersonFragmentArgs by navArgs()

    @Inject lateinit var wishesAdapter : SelectWishesAdapter
    @Inject lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        injectDependencies()
        binding = FragmentPersonBinding.inflate(inflater,container,false)
        viewModel.currentPerson = args.person
        return binding.root
    }

    private fun injectDependencies() {
        DaggerPersonComponent.builder()
            .daoModule(DaoModule(application()))
            .personModule(PersonModule(mainActivity()))
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerWishesRecycler()
        configureButtons()
        bindPersonData()
        observe()
        setTitle(R.string.person)
    }

    private fun configureRecyclerWishesRecycler() {
        wishesRecycler.apply {
            adapter = wishesAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun configureButtons() {
        sendButton.setOnClickListener { sendWishes() }
        shareButton.setOnClickListener { shareWishes() }
    }

    private fun sendWishes() { viewModel.sendWishes(selectedWishes()) }

    private fun shareWishes() { viewModel.shareWishes(selectedWishes()) }

    private fun selectedWishes() = wishesAdapter.getSelectedWishes()

    private fun bindPersonData() {
        binding.personItem = viewModel.currentPerson
        binding.executePendingBindings()
    }

    private fun observe() {
        viewModel.allWishes.observe(this, Observer { wishes -> wishesAdapter.submitList(wishes) })
        wishesAdapter.isAnyItemSelected.observe(this, getSelectionObserver() )
    }

    private fun getSelectionObserver() = Observer<Boolean> { isItemSelected ->
        shareButton.isEnabled = isItemSelected
        sendButton.isEnabled = isItemSelected && phoneNumberIsNotEmpty()
    }

    private fun phoneNumberIsNotEmpty() = viewModel.currentPerson.phoneNumber.isNotEmpty()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.person_fragment_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_edit_person)
            navigateToEditPersonFragment()

        return super.onOptionsItemSelected(item)
    }

    private fun navigateToEditPersonFragment() {
        val navAction = PersonFragmentDirections.actionPersonFragmentToEditPersonFragment((viewModel.currentPerson))
        findNavController().navigate(navAction)
    }

    interface ViewModel {
        val allWishes: LiveData<List<Wishes>>
        var currentPerson: Person

        fun sendWishes(wishes: Wishes)
        fun shareWishes(wishes: Wishes)
    }

}
