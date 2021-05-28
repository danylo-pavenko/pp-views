package com.dansdev.app.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.dansdev.app.util.onAttachWindow

open class PDAutoCompleteTextView : AppCompatAutoCompleteTextView, IPerfectDesignView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initSizes(isInEditMode, context, attrs)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, percentTextSize)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onAttachWindow(this)
    }

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
}
