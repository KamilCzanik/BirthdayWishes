package com.example.birthdaywishes

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

    fun arePermissionsGranted(permissions: Array<String>): Boolean {
        for (permission in permissions)
            if (!isPermissionGranted(permission))
                return false
        return true
    }

    fun requestPermission(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(
            mainActivity,
            permissions,
            requestCode
        )
    }
}
