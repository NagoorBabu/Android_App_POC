package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.R
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.auth.AuthScope
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.UICommunicationListener
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth.state.AuthStateEvent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth.state.LoginFields
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
@AuthScope
class LoginFragment
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_login) {

    private val TAG: String = "AppDebug"

    lateinit var uiCommunicationListener: UICommunicationListener

    val viewModel: AuthViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()

        login_button.setOnClickListener {
            login()
        }
    }

    fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.loginFields?.let {
                it.login_email?.let { input_email.setText(it) }
                it.login_password?.let { input_password.setText(it) }
            }
        })
    }

    fun login() {
        viewModel.setStateEvent(
            AuthStateEvent.LoginAttemptEvent(
                input_email.text.toString(),
                input_password.text.toString()
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginFields(
            LoginFields(
                input_email.text.toString(),
                input_password.text.toString()
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }
    }

}
