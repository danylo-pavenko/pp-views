package com.dansdev.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dansdev.app.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onStart() {
        super.onStart()
        PerfectDesignIniter.onStart(this)
    }
}