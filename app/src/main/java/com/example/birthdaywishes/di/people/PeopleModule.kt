package com.example.birthdaywishes.di.people

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.birthdaywishes.repository.PeopleRepository
import com.example.birthdaywishes.ui.PeopleFragment
import com.example.birthdaywishes.viewmodel.PeopleViewModel
import dagger.Module
import dagger.Provides

@Module
class PeopleModule(private val application: Application,private val fragment: PeopleFragment) {

    @Provides
    fun providesViewModel(repository: PeopleRepository) : PeopleFragment.ViewModel {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(PeopleViewModel::class.java)
        viewModel.repository = repository
        return viewModel
    }

    @Provides
    fun providesOnPersonItemClickListener() = fragment.onPersonItemClickListener

}