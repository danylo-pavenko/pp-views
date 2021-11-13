package com.dansdev.app.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatEditText
import com.dansdev.app.util.onAttachWindow

open class PDEditText : AppCompatEditText, IPerfectDesignView {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        initSizes(isInEditMode, context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle) {
        initSizes(isInEditMode, context, attrs)
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

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, percentTextSize)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onAttachWindow(this)
    }
}