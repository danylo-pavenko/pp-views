package com.dansdev.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.dansdev.app.util.onAttachWindow
import com.google.android.material.button.MaterialButton

open class PDMaterialButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialButton(context, attrs, defStyle), IPerfectDesignView {

    override var percentMarginTop: Int = 0
    override var percentMarginBottom: Int = 0
    override var percentMarginStart: Int = 0
    override var percentMarginEnd: Int = 0
    override var percentHeight: Int = 0
    override var percentWidth: Int = 0
    override var percentPaddingStart: Int = 0
    override var percentPaddingEnd: Int = 0
    override var percentPaddingTop: Int = 0
    override var percentPaddingBottom: Int = 0
    override var percentTextSize: Float = textSize

    init {
        initSizes(isInEditMode, context, attrs)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onAttachWindow(this)
    }

    @SuppressLint("RestrictedApi")
    fun setTintColor(color: Int) {
        supportBackgroundTintList = ColorStateList.valueOf(color)
    }

    @SuppressLint("RestrictedApi")
    fun setTintResColor(@ColorRes colorResId: Int) {
        supportBackgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, colorResId))
    }
}
