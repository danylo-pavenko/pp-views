package com.dansdev.pp_views.util

class PercentSizeManager(val screenParams: ScreenParams) {

    companion object {
        private const val DEFAULT_WIDTH = 360f
        private const val DEFAULT_HEIGHT = 640f
        private const val LONG_HEIGHT = 740f
    }

    fun height(default: Float, long: Float = 0f) = if (!isLong) {
        screenParams.screenHeight.percent(default * 100 / DEFAULT_HEIGHT)
    } else {
        screenParams.screenHeight.percent((if (long == 0f) default else long) * 100 / LONG_HEIGHT)
    }

    fun width(default: Float, long: Float = 0f, lookDefault: Boolean = true) = if (!isLong) {
        screenParams.screenWidth.percent(default * 100 / DEFAULT_WIDTH)
    } else {
        screenParams.screenWidth.percent((if (long == 0f && lookDefault) default else long) * 100 / DEFAULT_WIDTH)
    }

    val isLong: Boolean get() = screenParams.isLong
}
