package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.blog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.R
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.DataStateChangeListener
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.UICommunicationListener
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.blog.state.BLOG_VIEW_STATE_BUNDLE_KEY
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.blog.state.BlogViewState
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.blog.viewmodel.BlogViewModel

abstract class BaseBlogFragment
constructor(
    @LayoutRes
    private val layoutRes: Int
) : Fragment(layoutRes) {

    val TAG: String = "AppDebug"

    lateinit var uiCommunicationListener: UICommunicationListener

    lateinit var stateChangeListener: DataStateChangeListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBarWithNavController(R.id.blogFragment, activity as AppCompatActivity)
    }

    abstract fun cancelActiveJobs()

    fun setupActionBarWithNavController(fragmentId: Int, activity: AppCompatActivity) {
        val appBarConfiguration = AppBarConfiguration(setOf(fragmentId))
        NavigationUI.setupActionBarWithNavController(
            activity,
            findNavController(),
            appBarConfiguration
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            stateChangeListener = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement DataStateChangeListener")
        }

        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }

    }
}