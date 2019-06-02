package com.example.birthdaywishes

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.slideDownAndDisappear() {
    animate()
        .translationX(0f)
        .alpha(0f)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                visibility = GONE
            }
        })
}

fun View.appearAndSlideUp() {
    visibility = VISIBLE
    alpha = 0f
    animate()
        .translationY(height.toFloat())
        .alpha(1.0f)
        .setListener(null)
}