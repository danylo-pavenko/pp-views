package com.dansdev.app.util

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Size
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.dansdev.app.R
import com.livinglifetechway.k4kotlin.core.orZero
import kotlin.math.roundToInt

private const val LONG_RATIO = 1.8f

fun Activity.rootContainer(): ViewGroup =
    (findViewById<ViewGroup>(android.R.id.content)).getChildAt(0) as ViewGroup

fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = dpToPxFloat(50).roundToInt()
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}

fun Activity.isLong(): Boolean =
    screenSize().height.toFloat() / screenSize().width.toFloat() > LONG_RATIO

fun Activity.screenSize(): Size {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return Size(size.x, size.y)
}

fun Activity.hasCutout(): Boolean {
    val density = resources.displayMetrics.density
    return ((resources.displayMetrics.heightPixels - rootContainer().height) < 0 && (statusBarHeight() / density) != 24f) || (statusBarHeight() / density) > 28 //24dp is default height for android devices status bar, but for samsung and rounded devices 24 < x < 28
}

fun Activity.screenParamsHeight() = screenSize().height

fun Activity.screenParamsStatusBarHeight() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    window?.decorView?.rootWindowInsets?.systemWindowInsetTop.orZero()
} else {
    statusBarHeight()
}

fun Activity.screenParamsNavigationBarHeight() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && withNavigationBar()) {
        window?.decorView?.rootWindowInsets?.systemWindowInsetBottom.orZero()
    } else {
        navigationBarHeight()
    }


fun Activity.withNavigationBar() =
    resources.getBoolean(resources.getIdentifier("config_showNavigationBar", "bool", "android"))

fun Context.statusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Context.color(@ColorRes resId: Int): Int = ContextCompat.getColor(this, resId)

fun Context.drawable(@DrawableRes resId: Int): Drawable? = ContextCompat.getDrawable(this, resId)

fun Context.toolBarHeight(): Int {
    val attrs = intArrayOf(R.attr.actionBarSize)
    val typedArray = obtainStyledAttributes(attrs)
    val toolBarHeight = typedArray.getDimensionPixelSize(0, -1)
    typedArray.recycle()
    return toolBarHeight
}

fun Context.navigationBarHeight(): Int {
    val resources = resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else 0
}

fun Context.dpToPx(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.dpToPxFloat(value: Int): Float = (value * resources.displayMetrics.density)

fun Context.dpToPx(value: Float): Int = (value * resources.displayMetrics.density).toInt()

fun Context.dpToPxFloat(value: Float): Float = (value * resources.displayMetrics.density)

