package com.dansdev.app.util

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.dansdev.app.storage.PDSizeStorage
import com.livinglifetechway.k4kotlin.core.androidx.color

private val View.sizeManager
    get() = PDSizeStorage.instance.run {
        PercentSizeManager(screenParams)
    }

fun View.setBackTint(@ColorInt color: Int) {
    this.background?.mutate()?.setColorFilter(color, PorterDuff.Mode.SRC_IN) ?: run {
        this.backgroundTintList = ColorStateList.valueOf(color)
    }
}

fun View.setBackTintRes(@ColorRes color: Int) {
    this.background?.mutate()?.setColorFilter(context.color(color), PorterDuff.Mode.SRC_IN) ?: run {
        this.backgroundTintList = ColorStateList.valueOf(context.color(color))
    }
}

fun View.animateVisibility(
    visible: Boolean,
    duration: Long,
    endAnimationCallback: (() -> Unit)? = null
) {
    val targetAlpha = if (visible) 1f else 0f
    visibility = View.VISIBLE
    animate().alpha(targetAlpha).setDuration(duration).withEndAction {
        if (!visible) visibility = View.GONE
        postOnAnimation { endAnimationCallback?.invoke() }
    }
}

fun TextView.setCompoundDrawable(
    @DrawableRes iconLeftId: Int = 0,
    @DrawableRes iconTopId: Int = 0,
    @DrawableRes iconRightId: Int = 0,
    @DrawableRes iconBottomId: Int = 0
) {
    val drawableLeft = if (iconLeftId != 0) AppCompatResources.getDrawable(context, iconLeftId) else null
    val drawableTop = if (iconTopId != 0) AppCompatResources.getDrawable(context, iconTopId) else null
    val drawableRight = if (iconRightId != 0) AppCompatResources.getDrawable(context, iconRightId) else null
    val drawableBottom = if (iconBottomId != 0) AppCompatResources.getDrawable(context, iconBottomId) else null
    this.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
}

inline fun <reified T : ViewGroup.LayoutParams> View.updateLayoutParams(defaultBlock: ViewGroup.LayoutParams.() -> Unit, block: T.() -> Unit) {
    val params = layoutParams
    defaultBlock(params)
    layoutParams = params

    if (layoutParams is T) {
        val params = layoutParams as T
        block(params)
        layoutParams = params
    }
}
