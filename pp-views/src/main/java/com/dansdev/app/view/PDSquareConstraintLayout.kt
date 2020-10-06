package com.dansdev.app.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import com.dansdev.app.R
import com.dansdev.app.util.PercentSizeManager
import com.dansdev.app.storage.PDSizeStorage

open class PDSquareConstraintLayout : ConstraintLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initSizes(attrs)
    }

    private val sizeManager get() = PDSizeStorage.instance.run { PercentSizeManager(screenParams) }

    private var percentMarginTop = 0
    private var percentMarginBottom = 0
    private var percentMarginStart = 0
    private var percentMarginEnd = 0
    private var percentHeight = 0
    private var percentWidth = 0

    private fun initSizes(attrs: AttributeSet?) {
        if (isInEditMode) return
        attrs?.also {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.PDConstraintLayout)

            percentHeight = sizeManager.height(
                ta.getFloat(R.styleable.PDConstraintLayout_pd_height, 0f),
                ta.getFloat(R.styleable.PDConstraintLayout_pd_heightLong, 0f)
            )

            percentWidth = percentHeight

            percentMarginTop = sizeManager.height(
                ta.getFloat(R.styleable.PDConstraintLayout_pd_marginTop, 0f),
                ta.getFloat(R.styleable.PDConstraintLayout_pd_marginTopLong, 0f)
            )

            percentMarginBottom = sizeManager.height(
                ta.getFloat(R.styleable.PDConstraintLayout_pd_marginBottom, 0f),
                ta.getFloat(R.styleable.PDConstraintLayout_pd_marginBottomLong, 0f)
            )

            percentMarginStart =
                sizeManager.width(ta.getFloat(R.styleable.PDConstraintLayout_pd_marginStart, 0f))
            percentMarginEnd =
                sizeManager.width(ta.getFloat(R.styleable.PDConstraintLayout_pd_marginEnd, 0f))

            ta.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isInEditMode) return
        (layoutParams as? MarginLayoutParams)?.apply {
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

    fun setPDMargins(
            start: Int = 0,
            top: Int = 0,
            end: Int = 0,
            bottom: Int = 0
    ) {
        val correctStart = sizeManager.width(start.toFloat())
        val correctEnd = sizeManager.width(end.toFloat())
        val correctTop = sizeManager.height(top.toFloat())
        val correctBottom = sizeManager.height(bottom.toFloat())
        (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            setMargins(
                    if (correctStart != 0) correctStart else percentMarginStart,
                    if (correctTop != 0) correctTop else percentMarginTop,
                    if (correctEnd != 0) correctEnd else percentMarginEnd,
                    if (correctBottom != 0) correctBottom else percentMarginBottom
            )
        }
    }
}