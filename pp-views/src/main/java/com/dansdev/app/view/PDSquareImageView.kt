package com.dansdev.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.dansdev.app.R
import com.dansdev.app.util.PercentSizeManager
import com.dansdev.app.storage.PDSizeStorage

/**
 * WIDTH DEPENDS ON HEIGHT,
 * SET WIDTH WILL NOT AFFECT !!!
 * JUST SET HEIGHT !!!
 */
class PDSquareImageView : AppCompatImageView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
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
    private var percentPadding = 0

    @SuppressLint("CustomViewStyleable")
    private fun initSizes(attrs: AttributeSet?) {
        attrs?.also {
            if (isInEditMode) return
            val ta = context.obtainStyledAttributes(attrs, R.styleable.PDSquareImageView)

            percentMarginStart =
                sizeManager.width(ta.getFloat(R.styleable.PDSquareImageView_pd_marginStart, 0f))
            percentMarginEnd =
                sizeManager.width(ta.getFloat(R.styleable.PDSquareImageView_pd_marginEnd, 0f))

            percentHeight = sizeManager.height(
                ta.getFloat(R.styleable.PDSquareImageView_pd_height, 0f),
                ta.getFloat(R.styleable.PDSquareImageView_pd_heightLong, 0f)
            )

            percentMarginTop = sizeManager.height(
                ta.getFloat(R.styleable.PDSquareImageView_pd_marginTop, 0f),
                ta.getFloat(R.styleable.PDSquareImageView_pd_marginTopLong, 0f)
            )

            percentMarginBottom = sizeManager.height(
                ta.getFloat(R.styleable.PDSquareImageView_pd_marginBottom, 0f),
                ta.getFloat(R.styleable.PDSquareImageView_pd_marginBottomLong, 0f)
            )

            percentPadding = sizeManager.height(
                ta.getFloat(R.styleable.PDPercentSizes_pd_padding, 0f),
                ta.getFloat(R.styleable.PDPercentSizes_pd_padding, 0f)
            )
            ta.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isInEditMode) return
        (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            if (percentHeight != 0) {
                height = percentHeight
                width = percentHeight
            }
            setMargins(
                if (percentMarginStart != 0) percentMarginStart else marginStart,
                if (percentMarginTop != 0) percentMarginTop else marginTop,
                if (percentMarginEnd != 0) percentMarginEnd else marginEnd,
                if (percentMarginBottom != 0) percentMarginBottom else marginBottom
            )
            if (percentPadding != 0) {
                setPadding(0, percentPadding, percentPadding, 0)
            }
        }

        requestLayout()
    }

    fun setSize(sizeDefault: Float, sizeLong: Float = 0f) {
        percentHeight = sizeManager.height(sizeDefault, sizeLong)
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            width = percentHeight
            height = percentHeight
        }
    }

    fun setPDMargins(
        start: Float = 0f,
        top: Float = 0f,
        end: Float = 0f,
        bottom: Float = 0f
    ) {
        val correctStart = sizeManager.width(start)
        val correctEnd = sizeManager.width(end)
        val correctTop = sizeManager.height(top)
        val correctBottom = sizeManager.height(bottom)
        (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            setMargins(
                if (correctStart != 0) correctStart else percentMarginStart,
                if (correctTop != 0) correctTop else percentMarginTop,
                if (correctEnd != 0) correctEnd else percentMarginEnd,
                if (correctBottom != 0) correctBottom else percentMarginBottom
            )
        }
    }

    fun updateSize(size: Int) {
        percentHeight = size
        updateLayoutParams {
            width = size
            height = size
        }
    }

    inline fun <reified T : ViewGroup.MarginLayoutParams> setMargin(
        top: Int = 0,
        bottom: Int = 0,
        start: Int = 0,
        end: Int = 0
    ) {
        updateLayoutParams<T> {
            setMargins(start, top, end, bottom)
        }
    }
}
