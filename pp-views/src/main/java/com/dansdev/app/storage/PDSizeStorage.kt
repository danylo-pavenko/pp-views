package com.dansdev.app.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.dansdev.app.util.ScreenParams

class PDSizeStorage private constructor(context: Context) {

    companion object {
        private const val NAME = "perfect_design_prefs"
        private const val SCREEN_HEIGHT = "screen_height"
        private const val SCREEN_WIDTH = "screen_width"
        private const val IS_LONG = "is_long"
        private const val HAS_BANGS = "has_bangs"
        private const val NAVIGATION_HEIGHT = "navigation_height"
        private const val TOOLBAR_HEIGHT = "toolbar_height"
        private const val STATUSBAR_HEIGHT = "statusbar_height"
        private const val KEYBOARD_HEIGHT = "keyboard_height"

        lateinit var instance: PDSizeStorage

        fun init(context: Context) {
            if (!::instance.isInitialized) instance = PDSizeStorage(context)
        }
    }

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    private var screenHeight: Int
        get() = sharedPrefs.getInt(SCREEN_HEIGHT, 0)
        set(value) = sharedPrefs.edit { putInt(SCREEN_HEIGHT, value) }

    private var screenWidth: Int
        get() = sharedPrefs.getInt(SCREEN_WIDTH, 0)
        set(value) = sharedPrefs.edit { putInt(SCREEN_WIDTH, value) }

    private var isLong: Boolean
        get() = sharedPrefs.getBoolean(IS_LONG, false)
        set(value) = sharedPrefs.edit { putBoolean(IS_LONG, value) }

    private var hasBangs: Boolean
        get() = sharedPrefs.getBoolean(HAS_BANGS, false)
        set(value) = sharedPrefs.edit { putBoolean(HAS_BANGS, value) }

    private var navigationHeight: Int
        get() = sharedPrefs.getInt(NAVIGATION_HEIGHT, 0)
        set(value) = sharedPrefs.edit { putInt(NAVIGATION_HEIGHT, value) }

    private var toolbarHeight: Int
        get() = sharedPrefs.getInt(TOOLBAR_HEIGHT, 0)
        set(value) = sharedPrefs.edit { putInt(TOOLBAR_HEIGHT, value) }

    private var statusBarHeight: Int
        get() = sharedPrefs.getInt(STATUSBAR_HEIGHT, 0)
        set(value) = sharedPrefs.edit { putInt(STATUSBAR_HEIGHT, value) }

    private var keyboardHeight: Int
        get() = sharedPrefs.getInt(KEYBOARD_HEIGHT, 0)
        set(value) = sharedPrefs.edit { putInt(KEYBOARD_HEIGHT, value) }

    var screenParams: ScreenParams
        get() = ScreenParams(
            screenHeight,
            screenWidth,
            isLong,
            hasBangs,
            navigationHeight,
            toolbarHeight,
            statusBarHeight,
            keyboardHeight
        )
        set(value) {
            screenHeight = value.screenHeight
            screenWidth = value.screenWidth
            isLong = value.isLong
            hasBangs = value.hasBangs
            navigationHeight = value.navigationHeight
            toolbarHeight = value.toolbarHeight
            statusBarHeight = value.statusBarHeight
            keyboardHeight = value.keyboardHeight
        }
}
