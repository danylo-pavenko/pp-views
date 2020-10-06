package com.dansdev.pp_views.util

import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnAttach
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.databinding.BindingAdapter
import com.dansdev.pp_views.storage.PDSizeStorageImpl
import android.animation.Animator
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.livinglifetechway.k4kotlin.core.androidx.color

private val View.sizeManager
    get() = PDSizeStorageImpl(context).run {
        PercentSizeManager(screenParams)
    }


@BindingAdapter(
    "percent_width",
    "percent_height",
    "percent_heightLong",
    "percent_textSize",
    "percent_textSizeLong",
    "percent_marginTop",
    "percent_marginTopLong",
    "percent_marginBottom",
    "percent_marginBottomLong",
    "percent_marginStart",
    "percent_marginEnd"
)
fun View.setPercentValues(
    percentWidth: Int = 0,
    percentHeight: Int = 0,
    percentHeightLong: Int = 0,
    percentTextSize: Int = 0,
    percentTextSizeLong: Int = 0,
    percentMarginTop: Int = 0,
    percentMarginTopLong: Int = 0,
    percentMarginBottom: Int = 0,
    percentMarginBottomLong: Int = 0,
    percentMarginStart: Int = 0,
    percentMarginEnd: Int = 0
) {
    doOnAttach {
        if (!isInEditMode) {
            (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                if (percentHeight != 0) height = percentHeight
                if (percentWidth != 0) width = percentWidth
                setMargins(
                    if (percentMarginStart != 0) percentMarginStart else marginStart,
                    if (percentMarginTop != 0) percentMarginTop else marginTop,
                    if (percentMarginEnd != 0) percentMarginEnd else marginEnd,
                    if (percentMarginBottom != 0) percentMarginBottom else marginBottom
                )
            }
            requestLayout()
        }
    }
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
