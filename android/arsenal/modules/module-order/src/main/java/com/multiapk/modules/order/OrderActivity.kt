package com.multiapk.modules.order

import android.os.Bundle
import android.widget.FrameLayout
import org.smartrobot.base.DefaultBaseActivity

class OrderActivity : DefaultBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this))
        supportFragmentManager.beginTransaction().add(android.R.id.content, OrderFragment(), OrderFragment::javaClass.name).commitAllowingStateLoss()
    }
}