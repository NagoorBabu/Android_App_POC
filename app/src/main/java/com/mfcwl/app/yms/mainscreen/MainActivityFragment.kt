package com.mfcwl.app.yms.mainscreen

import com.mfcwl.app.yms.R
import com.mfcwl.app.yms.platform.BaseFragment

class MainActivityFragment : BaseFragment() {

    companion object {
        fun getMainActivityFragment() = MainActivityFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_activity
    }
}