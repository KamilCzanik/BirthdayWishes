package com.example.birthdaywishes.system

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.birthdaywishes.ui.MainActivity
import javax.inject.Inject


class PermissionManager @Inject constructor(private val mainActivity: MainActivity) {


    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            mainActivity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(
            mainActivity,
            permissions,
            requestCode
        )
    }
}
