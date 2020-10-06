package com.dansdev.pp_views.util

import androidx.annotation.Keep

@Keep
data class ScreenParams(
    val screenHeight: Int,
    val screenWidth: Int,
    val isLong: Boolean,
    val hasBangs: Boolean,
    val navigationHeight: Int,
    val toolbarHeight: Int,
    val statusBarHeight: Int,
    var keyboardHeight: Int = 0
) {
    fun screenHeightWithoutNavBar(): Int = screenHeight - navigationHeight
}

/**
 * calculate with size
 */
fun ScreenParams.width(value: Float): Int = screenWidth.percent(value)

/**
 * calculate height size without navigation bar
 */
fun ScreenParams.heightNav(default: Float, long: Float? = null): Int = if (isLong && long != null) {
    screenHeightWithoutNavBar().percent(long)
} else {
    screenHeightWithoutNavBar().percent(default)
}

fun ScreenParams.setHeight(default: Float, long: Float? = null): Int = if (isLong && long != null) {
    screenHeight.percent(long)
} else {
    screenHeight.percent(default)
}