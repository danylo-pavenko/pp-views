package com.dansdev.app

import android.app.Activity
import android.app.Application
import android.util.Log
import com.dansdev.app.storage.PDSizeStorage
import com.dansdev.app.util.*

/**
 * For work of lib need to setup application and first activity onStart
 *
 * if you use keyboard, need to save it height
 */
object PerfectDesignIniter {

    fun init(application: Application) {
        PDSizeStorage.init(application)
    }

    fun onStart(activity: Activity) {
        val oldParams = PDSizeStorage.instance.screenParams
        val params = ScreenParams(
            if (oldParams.screenHeight == 0) activity.screenParamsHeight() else oldParams.screenHeight,
            activity.screenSize().width,
            activity.isLong(),
            activity.hasCutout(),
            if (oldParams.navigationHeight == 0) activity.screenParamsNavigationBarHeight() else oldParams.navigationHeight,
            activity.toolBarHeight(),
            if (oldParams.statusBarHeight == 0) activity.screenParamsStatusBarHeight() else oldParams.statusBarHeight,
            oldParams.keyboardHeight
        )
        PDSizeStorage.instance.screenParams = params
    }

    /**
     * You can use utils from keyboard package of library
     */
    fun onOpenKeyboard(height: Int) {
        if (height > 0) {
            val oldParams = PDSizeStorage.instance.screenParams
            oldParams.keyboardHeight = height
            PDSizeStorage.instance.screenParams = oldParams
        } else {
            Log.d(javaClass.simpleName, "keyboard height is ZERO")
        }
    }
}