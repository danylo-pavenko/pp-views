package com.dansdev.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import com.dansdev.app.R
import com.dansdev.app.util.PercentSizeManager
import com.dansdev.app.storage.PDSizeStorage
import com.livinglifetechway.k4kotlin.core.hide
import com.livinglifetechway.k4kotlin.core.show

private const val IMAGE_POSITION_START = 1
private const val IMAGE_POSITION_END = 2

open class PDButtonWithImage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : PDConstraintLayout(context, attrs, defStyleAttr) {

    private val sizeManager get() = PDSizeStorage.instance.run { PercentSizeManager(screenParams) }

    private val textView: PDTextView
    private val imageView: PDSquareImageView

    init {
        textView = PDTextView(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            this@PDButtonWithImage.addView(this)
        }
        imageView = PDSquareImageView(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            this@PDButtonWithImage.addView(this)
        }
        setupButtonAttributes(attrs)
    }

    @SuppressLint("ResourceAsColor")
    private fun setupButtonAttributes(attrs: AttributeSet?) {
        if (isInEditMode) return
        attrs?.apply {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.PDButtonWithImage)
            ta.getString(R.styleable.PDButtonWithImage_PDButtonWithImage_font)?.also {
                textView.typeface = Typeface.createFromAsset(context.assets, "font/$it.ttf")
            }
            ta.getColor(R.styleable.PDButtonWithImage_PDButtonWithImage_text_color, Color.WHITE).also {
                textView.setTextColor(it)
            }
            ta.getFloat(R.styleable.PDButtonWithImage_PDButtonWithImage_imageSize, 0f).also {
                val size = sizeManager.height(it)
                imageView.updateLayoutParams<LayoutParams> {
                    width = size
                    height = size
                }
            }
            ta.getResourceId(R.styleable.PDButtonWithImage_PDButtonWithImage_drawable, -1).also {
                setBackgroundResource(it)
            }
            val percentTextSize = ta.getFloat(R.styleable.PDButtonWithImage_PDButtonWithImage_textSize, 0f)
            val percentTextSizeLong = ta.getFloat(R.styleable.PDButtonWithImage_PDButtonWithImage_textSizeLong, 0f)
            textView.setPDTextSize(sizeManager.height(percentTextSize, percentTextSizeLong).toFloat())
            ta.getBoolean(R.styleable.PDButtonWithImage_PDButtonWithImage_textAllCaps, false).also {
                textView.isAllCaps = it
            }
            val marginImage =
                sizeManager.width(ta.getFloat(R.styleable.PDButtonWithImage_PDButtonWithImage_imageMargin, 0f))
            ta.getInt(R.styleable.PDButtonWithImage_PDButtonWithImage_imagePosition, 1).also {
                setTextPosition(it, marginImage)
            }
            ta.recycle()
        }
    }

    private fun setTextPosition(type: Int, marginImage: Int) {
        when (type) {
            IMAGE_POSITION_START -> {
                ConstraintSet().apply {
                    clone(this@PDButtonWithImage)
                    /**
                     * setup ImageView
                     */
                    connect(imageView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    connect(imageView.id, ConstraintSet.END, textView.id, ConstraintSet.START, marginImage)
                    connect(imageView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    connect(imageView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                    setHorizontalChainStyle(imageView.id, ConstraintSet.CHAIN_PACKED)
                    /**
                     * setup TextView
                     */
                    connect(textView.id, ConstraintSet.START, imageView.id, ConstraintSet.END)
                    connect(textView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                    connect(textView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                    connect(textView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    applyTo(this@PDButtonWithImage)
                }
            }
            IMAGE_POSITION_END -> {
                ConstraintSet().apply {
                    clone(this@PDButtonWithImage)
                    /**
                     * setup TextView
                     */
                    connect(textView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    connect(textView.id, ConstraintSet.END, imageView.id, ConstraintSet.START, marginImage)
                    connect(textView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                    connect(textView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    setHorizontalChainStyle(textView.id, ConstraintSet.CHAIN_PACKED)
                    /**
                     * setup ImageView
                     */
                    connect(imageView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    connect(imageView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                    connect(imageView.id, ConstraintSet.START, textView.id, ConstraintSet.END)
                    connect(imageView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                    applyTo(this@PDButtonWithImage)
                }
            }
        }
    }

    fun setText(@StringRes textRes: Int) {
        textView.setText(textRes)
    }

    fun setText(text: String) {
        textView.text = text
    }

    fun setDrawableRes(@DrawableRes drawableRes: Int?) {
        drawableRes?.also {
            imageView.setImageResource(it)
            imageView.show()
        } ?: also {
            imageView.hide()
        }
    }
}
