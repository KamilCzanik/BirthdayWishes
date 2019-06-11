package com.example.birthdaywishes

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewPropertyAnimator
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.birthdaywishes.ui.MainActivity

fun View.slideDownAndDisappear() {
    animate()
        .translationY(height.toFloat())
        .withEndAction { visibility = GONE }
}

fun View.appearAndSlideUp(): ViewPropertyAnimator {
    visibility = VISIBLE
    return animate()
        .translationY(0.0f)
}

fun Fragment.showLongToast(resId: Int) {
    Toast.makeText(context,resId,Toast.LENGTH_LONG).show()
}

fun Fragment.application() = activity?.application!!

fun Fragment.mainActivity() = activity as MainActivity

fun Fragment.finishFragment() = activity?.onBackPressed()