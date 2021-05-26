package com.dansdev.app.util

import android.view.View
import android.view.ViewGroup

fun Int.percent(percent: Float): Int = (this * (percent / 100)).toInt()

inline fun <reified T : ViewGroup.LayoutParams> View.setWidth(width: Int) {
    val params = layoutParams as T
    params.width = width
    this.requestLayout()
}

inline fun <reified T : ViewGroup.LayoutParams> View.setHeight(height: Int) {
    val params = layoutParams as T
    params.height = height
    this.requestLayout()
}
