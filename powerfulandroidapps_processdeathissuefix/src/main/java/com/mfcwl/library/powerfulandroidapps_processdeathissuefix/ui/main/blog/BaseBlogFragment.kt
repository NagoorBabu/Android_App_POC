package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.RequestManager
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.R
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.Injectable
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.DataStateChangeListener
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.UICommunicationListener
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.MainDependencyProvider
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.state.BLOG_VIEW_STATE_BUNDLE_KEY
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.state.BlogViewState
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.viewmodel.BlogViewModel
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseBlogFragment : Fragment(), Injectable {

    val TAG: String = "AppDebug"

    lateinit var dependencyProvider: MainDependencyProvider

    lateinit var uiCommunicationListener: UICommunicationListener

    lateinit var stateChangeListener: DataStateChangeListener

    lateinit var viewModel: BlogViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBarWithNavController(R.id.blogFragment, activity as AppCompatActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(
                this,
                dependencyProvider.getVMProviderFactory()
            ).get(BlogViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        cancelActiveJobs()

        // Restore state after process death
        savedInstanceState?.let { inState ->
            (inState[BLOG_VIEW_STATE_BUNDLE_KEY] as BlogViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    fun cancelActiveJobs() {
        viewModel.cancelActiveJobs()
    }

    fun isViewModelInitialized() = ::viewModel.isInitialized

    /**
     * !IMPORTANT!
     * Must save ViewState b/c in event of process death the LiveData in ViewModel will be lost
     */
    override fun onSaveInstanceState(outState: Bundle) {
        if (isViewModelInitialized()) {
            val viewState = viewModel.viewState.value

            //clear the list. Don't want to save a large list to bundle.
            viewState?.blogFields?.blogList = ArrayList()

            outState.putParcelable(
                BLOG_VIEW_STATE_BUNDLE_KEY,
                viewState
            )
        }
        super.onSaveInstanceState(outState)
    }

    /*
          @fragmentId is id of fragment from graph to be EXCLUDED from action back bar nav
        */
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

        try {
            dependencyProvider = context as MainDependencyProvider
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement DependencyProvider")
        }
    }
}