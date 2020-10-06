package com.dansdev.pp_views.util

import android.view.View
import android.view.ViewGroup

fun Int.percent(percent: Float): Int = (this * (percent / 100)).toInt()

fun String.removeAllNonDigitSymbols(): String {
    val regex = Regex("[^0-9\\S]|[^0-9]")
    return this.replace(regex, "")
}

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
