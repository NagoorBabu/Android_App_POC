package com.mfcwl.app.yms.mainscreen

import android.os.Bundle
import com.mfcwl.app.yms.R
import com.mfcwl.app.yms.platform.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment()
    }

    fun addFragment() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.base_frame_layout) as MainActivityFragment?
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.base_frame_layout, MainActivityFragment.getMainActivityFragment())
                .commit()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

}