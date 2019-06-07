package com.example.birthdaywishes

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewPropertyAnimator

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