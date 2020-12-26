package com.dansdev.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.dansdev.app.R
import com.dansdev.app.storage.PDSizeStorage
import com.dansdev.app.util.PercentSizeManager
import com.dansdev.library_autofittextview.AutofitTextView

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

    private val sizeManager get() = PDSizeStorage.instance.run { PercentSizeManager(screenParams) }

    private var percentMarginTop = 0
    private var percentMarginBottom = 0
    private var percentMarginStart = 0
    private var percentMarginEnd = 0
    private var percentHeight = 0
    private var percentWidth = 0
    private var percentPaddingStart = 0
    private var percentPaddingEnd = 0
    private var percentPaddingTop = 0
    private var percentPaddingBottom = 0

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

            percentPaddingStart = sizeManager.width(ta.getFloat(R.styleable.PDPercentSizes_pd_paddingStart, 0f))
            percentPaddingEnd = sizeManager.width(ta.getFloat(R.styleable.PDPercentSizes_pd_paddingEnd, 0f))
            percentPaddingTop = sizeManager.height(
                ta.getFloat(R.styleable.PDPercentSizes_pd_paddingTop, 0f),
                ta.getFloat(R.styleable.PDPercentSizes_pd_paddingTopLong, 0f)
            )
            percentPaddingBottom = sizeManager.height(
                ta.getFloat(R.styleable.PDPercentSizes_pd_paddingBottom, 0f),
                ta.getFloat(R.styleable.PDPercentSizes_pd_paddingBottomLong, 0f)
            )

            setMaxTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            setMinTextSize(TypedValue.COMPLEX_UNIT_PX, minTextSize)

            ta.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isInEditMode) return
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            if (percentHeight != 0) height = percentHeight
            if (percentWidth != 0) width = percentWidth
            setMargins(
                percentMarginStart,
                percentMarginTop,
                percentMarginEnd,
                percentMarginBottom
            )
            setPadding(
                if (percentPaddingStart != 0) percentPaddingStart else paddingStart,
                if (percentPaddingTop != 0) percentPaddingTop else paddingTop,
                if (percentPaddingEnd != 0) percentPaddingEnd else paddingEnd,
                if (percentPaddingBottom != 0) percentPaddingBottom else paddingBottom
            )
        }

        requestLayout()
    }
}
