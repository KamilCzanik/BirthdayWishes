package com.example.birthdaywishes.viewmodel

import android.Manifest.permission.SEND_SMS
import android.app.Application
import android.telephony.SmsManager
import androidx.core.app.ShareCompat
import androidx.lifecycle.AndroidViewModel
import com.example.birthdaywishes.PermissionManager
import com.example.birthdaywishes.R
import com.example.birthdaywishes.data.Person
import com.example.birthdaywishes.data.Wishes
import com.example.birthdaywishes.db.WishesRepository
import com.example.birthdaywishes.ui.PersonFragment
import javax.inject.Inject

class PersonViewModel(application: Application) : AndroidViewModel(application),PersonFragment.ViewModel {

    @Inject lateinit var wishesRepository: WishesRepository
    @Inject lateinit var permissionManager: PermissionManager
    @Inject lateinit var smsManager: SmsManager
    @Inject lateinit var intentBuilder: ShareCompat.IntentBuilder

    override val allWishes by lazy { wishesRepository.wishes }

    override lateinit var currentPerson: Person

    override fun shareWishes(wishes: Wishes) =
        intentBuilder
        .setType("text/plain")
        .setText(wishes.content)
        .setChooserTitle(R.string.select_action_chooser)
        .startChooser()

    override fun sendWishes(wishes: Wishes) = if(arePermissionGranted()) permissionGranted(wishes) else permissionNotGranted()

    private fun arePermissionGranted() = permissionManager.isPermissionGranted(SEND_SMS)

    private fun permissionNotGranted() = permissionManager.requestPermission(arrayOf(SEND_SMS),5)

    private fun permissionGranted(wishes: Wishes) =
        smsManager.sendMultipartTextMessage(
            currentPerson.phoneNumber,
            null,
            smsManager.divideMessage(wishes.content),
            null,
            null)



}