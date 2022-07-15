package com.mfcwl.app.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main()
    }

    fun main() = runBlocking {

        val job = launch {
            delay(1000)
            Log.e("debug-coro", "World")
        }
        Log.e("debug-coro", "Hello")
        job.join()
        Log.e("debug-coro", "Done")
    }


}