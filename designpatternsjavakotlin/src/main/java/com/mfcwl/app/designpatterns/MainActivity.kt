package com.mfcwl.app.designpatterns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mfcwl.app.designpatterns.creationalpatterns.singleton.OneObjectClass
import com.mfcwl.app.designpatterns.creationalpatterns.singleton.Singleton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Singleton.getApplicationName()
        OneObjectClass.getApplicationName()
    }
}