package com.dansdev.app

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    fun onStart(activity: AppCompatActivity) {
        val oldParams = PDSizeStorage.instance.screenParams
        val params = ScreenParams(
            activity.screenParamsHeight(),
            activity.screenSize().width,
            activity.isLong(),
            false,
            activity.screenParamsNavigationBarHeight(),
            activity.toolBarHeight(),
            activity.screenParamsStatusBarHeight(),
            oldParams.keyboardHeight
        )
    }

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