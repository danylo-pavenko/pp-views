package com.dansdev.pp_views.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import com.dansdev.library_autofittextview.AutofitTextView
import com.dansdev.pp_views.R
import com.dansdev.pp_views.storage.PDSizeStorageImpl
import com.dansdev.pp_views.util.PercentSizeManager

open class PDAutoFitTextView : AutofitTextView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        includeFontPadding = false
        initSizes(attrs)
    }

    private val sizeManager get() = PDSizeStorageImpl(context).run { PercentSizeManager(screenParams) }

    private var percentMarginTop = 0
    private var percentMarginBottom = 0
    private var percentMarginStart = 0
    private var percentMarginEnd = 0
    private var percentHeight = 0
    private var percentWidth = 0

    @SuppressLint("CustomViewStyleable")
    private fun initSizes(attrs: AttributeSet?) {
        if (isInEditMode) return
        attrs?.also {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.PDAutoFitTextView)
            val textSize = sizeManager.height(
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_textSize, 0f),
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_textSizeLong, 0f)
            ).toFloat()

            val minTextSize = sizeManager.height(
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_minTextSize, 0f),
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_minTextSizeLong, 0f)
            ).toFloat()

            percentWidth =
                sizeManager.width(ta.getFloat(R.styleable.PDAutoFitTextView_pd_width, 0f))

            percentHeight = sizeManager.height(
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_height, 0f),
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_heightLong, 0f)
            )

            percentMarginTop = sizeManager.height(
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_marginTop, 0f),
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_marginTopLong, 0f)
            )

            percentMarginBottom = sizeManager.height(
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_marginBottom, 0f),
                ta.getFloat(R.styleable.PDAutoFitTextView_pd_marginBottomLong, 0f)
            )

            percentMarginStart =
                sizeManager.width(ta.getFloat(R.styleable.PDAutoFitTextView_pd_marginStart, 0f))
            percentMarginEnd =
                sizeManager.width(ta.getFloat(R.styleable.PDAutoFitTextView_pd_marginEnd, 0f))

            setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            setMinTextSize(TypedValue.COMPLEX_UNIT_PX, minTextSize)

            ta.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isInEditMode) return
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
