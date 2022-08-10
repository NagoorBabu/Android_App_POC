package com.mfcwl.app.yms.mainscreen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mfcwl.app.yms.R
import com.mfcwl.app.yms.platform.BaseActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment()
    }
















    fun addFragment() {
        /*val fragment =
            supportFragmentManager.findFragmentById(R.id.base_frame_layout) as MainActivityFragment?
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.base_frame_layout, MainActivityFragment.getMainActivityFragment())
                .commit()
        }*/
    }

    /*override fun getLayoutId(): Int {
        Log.e(TAG, "getLayoutId: called")
        return R.layout.activity_main
    }*/

}