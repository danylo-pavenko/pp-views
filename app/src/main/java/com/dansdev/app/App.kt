package com.dansdev.app

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PerfectDesignIniter.init(this)
    }
}