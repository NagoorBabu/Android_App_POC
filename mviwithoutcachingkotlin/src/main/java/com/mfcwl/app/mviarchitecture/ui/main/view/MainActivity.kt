package com.mfcwl.app.mviarchitecture.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mfcwl.app.mviarchitecture.R
import com.mfcwl.app.mviarchitecture.ui.common.DataStateListener
import com.mfcwl.app.mviarchitecture.ui.main.viewmodel.MainViewModel
import com.mfcwl.app.mviarchitecture.util.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    DataStateListener {

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        showMainFragment()
    }

    fun showMainFragment() {
        if (supportFragmentManager.fragments.size == 0) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    MainFragment(),
                    "MainFragment"
                )
                .commit()
        }
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let {

            showProgressbar(dataState.loading)

            dataState.message?.let { event ->
                event.getContentIfNotHandled()?.let { message ->
                    showToast(message)
                }

            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressbar(isVisible: Boolean) {
        if (isVisible) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
        }

    }

}