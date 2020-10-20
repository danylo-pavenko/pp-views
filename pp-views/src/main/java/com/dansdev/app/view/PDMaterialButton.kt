package com.dansdev.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import com.google.android.material.button.MaterialButton
import com.dansdev.app.R
import com.dansdev.app.util.PercentSizeManager
import com.dansdev.app.storage.PDSizeStorage
import com.dansdev.app.util.setBackTint

open class PDMaterialButton : MaterialButton {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        gravity = Gravity.CENTER
        initSizes(attrs)
    }

    private val sizeManager get() = PDSizeStorage.instance.run { PercentSizeManager(screenParams) }

    private var percentMarginTop = 0
    private var percentMarginBottom = 0
    private var percentMarginStart = 0
    private var percentMarginEnd = 0
    private var percentHeight = 0
    private var percentMinWidth = 0
    private var percentWidth = 0
    private var disabledColor = 0
    private var enabledColor = 0

    override fun isEnabled(): Boolean {
        if (super.isEnabled() && (enabledColor != 0 || disabledColor != 0)) {
            setBackTint(enabledColor)
        } else {
            setBackTint(disabledColor)
        }

        return super.isEnabled()
    }

    @SuppressLint("CustomViewStyleable")
    private fun initSizes(attrs: AttributeSet?) {
        if (isInEditMode) return
        attrs?.also {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.PDMaterialButton)
            val textSize = sizeManager.height(
                ta.getFloat(R.styleable.PDMaterialButton_pd_textSize, 0f),
                ta.getFloat(R.styleable.PDMaterialButton_pd_textSizeLong, 0f)
            ).toFloat()


            disabledColor = ta.getColor(R.styleable.PDMaterialButton_disabled_color, 0)
            enabledColor = ta.getColor(R.styleable.PDMaterialButton_disabled_color, 0)

            percentWidth = sizeManager.width(
                ta.getFloat(R.styleable.PDMaterialButton_pd_width, 0f)
            )

            percentMinWidth = sizeManager.width(
                ta.getFloat(R.styleable.PDMaterialButton_pd_minWidth, 0f)
            )

            percentHeight = sizeManager.height(
                ta.getFloat(R.styleable.PDMaterialButton_pd_height, 0f),
                ta.getFloat(R.styleable.PDMaterialButton_pd_heightLong, 0f)
            )

            percentMarginTop = sizeManager.height(
                ta.getFloat(R.styleable.PDMaterialButton_pd_marginTop, 0f),
                ta.getFloat(R.styleable.PDMaterialButton_pd_marginTopLong, 0f)
            )

            percentHeight = sizeManager.height(
                ta.getFloat(R.styleable.PDMaterialButton_pd_height, 0f),
                ta.getFloat(R.styleable.PDMaterialButton_pd_heightLong, 0f)
            )

            percentMarginBottom = sizeManager.height(
                ta.getFloat(R.styleable.PDMaterialButton_pd_marginBottom, 0f),
                ta.getFloat(R.styleable.PDMaterialButton_pd_marginBottomLong, 0f)
            )

            percentMarginStart =
                sizeManager.width(ta.getFloat(R.styleable.PDMaterialButton_pd_marginStart, 0f))
            percentMarginEnd =
                sizeManager.width(ta.getFloat(R.styleable.PDMaterialButton_pd_marginEnd, 0f))

            percentHeight = sizeManager.height(
                ta.getFloat(R.styleable.PDMaterialButton_pd_height, 0f),
                ta.getFloat(R.styleable.PDMaterialButton_pd_heightLong, 0f)
            )

            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

            ta.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isInEditMode) return
        (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            if (percentHeight != 0) height = percentHeight
            if (percentWidth != 0) width = percentWidth
            if (percentMinWidth != 0) minWidth = percentMinWidth
            setMargins(
                if (percentMarginStart != 0) percentMarginStart else marginStart,
                if (percentMarginTop != 0) percentMarginTop else marginTop,
                if (percentMarginEnd != 0) percentMarginEnd else marginEnd,
                if (percentMarginBottom != 0) percentMarginBottom else marginBottom
            )
        }

        requestLayout()
    }

    @SuppressLint("RestrictedApi")
    fun setTintColor(color: Int) {
        supportBackgroundTintList = ColorStateList.valueOf(color)
    }

    @SuppressLint("RestrictedApi")
    fun setTintResColor(@ColorRes colorResId: Int) {
        supportBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorResId))
    }
}
