package com.example.birthdaywishes.di.person

import android.telephony.SmsManager
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.example.birthdaywishes.PermissionManager
import com.example.birthdaywishes.db.WishesRepository
import com.example.birthdaywishes.ui.MainActivity
import com.example.birthdaywishes.ui.PersonFragment
import com.example.birthdaywishes.viewmodel.PersonViewModel
import dagger.Module
import dagger.Provides

@Module
class PersonModule(private val activity: MainActivity) {

    @Provides
    fun providesViewModel(repository: WishesRepository,permissionManager: PermissionManager) : PersonFragment.ViewModel {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application).create(PersonViewModel::class.java)
        viewModel.wishesRepository = repository
        viewModel.intentBuilder = ShareCompat.IntentBuilder.from(activity)
        viewModel.smsManager = SmsManager.getDefault()
        viewModel.permissionManager = permissionManager
        return viewModel
    }

    @Provides
    fun providesPermissionManager() = PermissionManager(activity)
}