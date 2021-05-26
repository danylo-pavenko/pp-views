package com.dansdev.app.util

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import com.dansdev.app.storage.PDSizeStorage
import com.dansdev.app.view.IPerfectDesignView
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

fun View.onAttachWindow(helper: IPerfectDesignView) {
    if (isInEditMode) return
    updateLayoutParams<ViewGroup.MarginLayoutParams>(
        defaultBlock = {
            if (helper.percentHeight != 0) height = helper.percentHeight
            if (width != ViewGroup.LayoutParams.MATCH_PARENT && helper.percentWidth != 0) width = helper.percentWidth
            setPadding(
                if (helper.percentPaddingStart != 0) helper.percentPaddingStart else paddingStart,
                if (helper.percentPaddingTop != 0) helper.percentPaddingTop else paddingTop,
                if (helper.percentPaddingEnd != 0) helper.percentPaddingEnd else paddingEnd,
                if (helper.percentPaddingBottom != 0) helper.percentPaddingBottom else paddingBottom
            )
        },
        block = {
            setMargins(
                if (helper.percentMarginStart != 0) helper.percentMarginStart else marginStart,
                if (helper.percentMarginTop != 0) helper.percentMarginTop else marginTop,
                if (helper.percentMarginEnd != 0) helper.percentMarginEnd else marginEnd,
                if (helper.percentMarginBottom != 0) helper.percentMarginBottom else marginBottom
            )
        }
    )

    requestLayout()
}

fun View.setPDHeight(dpHeight: Float, longDpHeight: Float) {
    if (this is IPerfectDesignView) this else return
    percentHeight = sizeManager.height(
        dpHeight,
        longDpHeight
    )
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        if (percentHeight != 0) height = percentHeight
    }
    requestLayout()
}

fun View.setPDWidth(dpWidth: Float, longDpWidth: Float) {
    if (this is IPerfectDesignView) this else return
    percentWidth = sizeManager.width(
        dpWidth,
        longDpWidth
    )
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        if (percentWidth != 0) width = percentWidth
    }
    requestLayout()
}

fun TextView.setPDTextSize(defaultDp: Float, longDp: Float) {
    if (this is IPerfectDesignView) this else return
    val textSize = sizeManager.height(defaultDp, longDp).toFloat()
    percentTextSize = textSize
    setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
}

