package com.dansdev.app

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(fragments.id, BlankFragment.newInstance("param1", "param2"))
            .commit()
    }

    override fun onStart() {
        super.onStart()
        PerfectDesignIniter.onStart(this)
    }
}
