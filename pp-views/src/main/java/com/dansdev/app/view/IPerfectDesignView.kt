package com.dansdev.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.dansdev.app.R
import com.dansdev.app.storage.PDSizeStorage
import com.dansdev.app.util.PercentSizeManager

interface IPerfectDesignView {
    val sizeManager get() = PDSizeStorage.instance.run { PercentSizeManager(screenParams) }

    var percentMarginTop: Int
    var percentMarginBottom: Int
    var percentMarginStart: Int
    var percentMarginEnd: Int
    var percentHeight: Int
    var percentWidth: Int
    var percentPaddingStart: Int
    var percentPaddingEnd: Int
    var percentPaddingTop: Int
    var percentPaddingBottom: Int
    var percentTextSize: Float

    @SuppressLint("CustomViewStyleable")
    fun initSizes(isInEditMode: Boolean, context: Context, attrs: AttributeSet?, square: Boolean = false) {
        if (isInEditMode || attrs == null) return
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PDPercentSizes)
        try {
            percentTextSize = sizeManager.height(
                ta.getFloat(R.styleable.PDPercentSizes_pd_textSize, 0f),
                ta.getFloat(R.styleable.PDPercentSizes_pd_textSizeLong, 0f)
            ).toFloat()

            percentHeight = sizeManager.height(
                ta.getFloat(R.styleable.PDPercentSizes_pd_height, 0f),
                ta.getFloat(R.styleable.PDPercentSizes_pd_heightLong, 0f)
            )

            percentWidth = if (square) {
                percentHeight
            } else {
                sizeManager.width(
                    ta.getFloat(R.styleable.PDPercentSizes_pd_width, 0f),
                    ta.getFloat(R.styleable.PDPercentSizes_pd_widthLong, 0f)
                )
            }

            percentMarginTop = sizeManager.height(
                ta.getFloat(R.styleable.PDPercentSizes_pd_marginTop, 0f),
                ta.getFloat(R.styleable.PDPercentSizes_pd_marginTopLong, 0f)
            )

            percentMarginBottom = sizeManager.height(
                ta.getFloat(R.styleable.PDPercentSizes_pd_marginBottom, 0f),
                ta.getFloat(R.styleable.PDPercentSizes_pd_marginBottomLong, 0f)
            )

            percentMarginStart = sizeManager.width(ta.getFloat(R.styleable.PDPercentSizes_pd_marginStart, 0f))
            percentMarginEnd = sizeManager.width(ta.getFloat(R.styleable.PDPercentSizes_pd_marginEnd, 0f))

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
        } finally {
            ta.recycle()
        }
    }
}
