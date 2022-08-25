package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.Injectable
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import java.lang.Exception
import javax.inject.Inject

abstract class BaseAuthFragment : Fragment(), Injectable {

    val TAG = "BaseAuthFragment"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        cancelActiveJobs()
    }

    private fun cancelActiveJobs() {
        viewModel.cancelActiveJobs()
    }
}