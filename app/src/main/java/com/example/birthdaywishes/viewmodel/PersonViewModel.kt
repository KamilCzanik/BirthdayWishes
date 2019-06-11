package com.example.birthdaywishes.viewmodel

import android.Manifest.permission.SEND_SMS
import android.app.Application
import android.telephony.SmsManager
import androidx.core.app.ShareCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.birthdaywishes.PermissionManager
import com.example.birthdaywishes.R
import com.example.birthdaywishes.event.EmptyWishesEvent
import com.example.birthdaywishes.event.ValidWishesEvent
import com.example.birthdaywishes.event.WishesEvent
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

    override val allWishes by lazy { wishesRepository.wishes }
    override val wishesEvent = MutableLiveData<WishesEvent>()

    override lateinit var currentPerson: Person

    override fun shareWishes(wishes: Wishes) {
        if(wishes.content.isNotEmpty()) {
            intentBuilder
                .setType("text/plain")
                .setText(wishes.content)
                .setChooserTitle(R.string.select_action_chooser)
                .startChooser()
            validWishesEvent()
        } else
            emptyWishesEvent()
    }

    override fun sendWishes(wishes: Wishes) {
        if (wishes.content.isNotEmpty()) {
            if(permissionManager.isPermissionGranted(SEND_SMS)) {
                permissionGranted(wishes)
                validWishesEvent()
            }
            else
                permissionNotGranted()
        } else
            emptyWishesEvent()
    }

    private fun emptyWishesEvent() { wishesEvent.value = EmptyWishesEvent() }

    private fun validWishesEvent() { wishesEvent.value = ValidWishesEvent() }

    private fun permissionGranted(wishes: Wishes) {
        smsManager.sendMultipartTextMessage(
            currentPerson.phoneNumber,
            null,
            smsManager.divideMessage(wishes.content),
            null,
            null)
    }

    private fun permissionNotGranted() {
        permissionManager.requestPermission(arrayOf(SEND_SMS),5)
    }
}