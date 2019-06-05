package com.example.birthdaywishes.viewmodel

import android.Manifest.permission.SEND_SMS
import android.app.Application
import android.telephony.SmsManager
import androidx.core.app.ShareCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.birthdaywishes.*
import com.example.birthdaywishes.pojo.Person
import com.example.birthdaywishes.pojo.Wishes
import com.example.birthdaywishes.repository.WishesRepository
import com.example.birthdaywishes.ui.PersonFragment
import javax.inject.Inject

class PersonViewModel(application: Application) : AndroidViewModel(application),PersonFragment.ViewModel {

    @Inject lateinit var wishesRepository: WishesRepository
    @Inject lateinit var permissionManager: PermissionManager
    @Inject lateinit var smsManager: SmsManager
    @Inject lateinit var intentBuilder: ShareCompat.IntentBuilder

    override val allWishes = wishesRepository.wishes
    override val permissionEvent = MutableLiveData<PermissionEvent>()

    override lateinit var currentPerson: Person

    override fun shareWishes(wishes: Wishes) {
        intentBuilder
            .setType("text/plain")
            .setText(wishes.content)
            .setChooserTitle(R.string.select_action_chooser)
            .startChooser()
    }

    override fun sendWishes(wishes: Wishes) {
        if(permissionManager.isPermissionGranted(SEND_SMS))
            permissionGranted(wishes)
        else
            permissionNotGranted()
    }

    private fun permissionGranted(wishes: Wishes) {
        smsManager.sendMultipartTextMessage(
            currentPerson.phoneNumber,
            null,
            smsManager.divideMessage(wishes.content),
            null,
            null)

        permissionEvent.value = PermissionGrantedEvent()
    }

    private fun permissionNotGranted() {
        permissionEvent.value = PermissionNotGrantedEvent()
        permissionManager.requestPermission(arrayOf(SEND_SMS),5)
    }
}