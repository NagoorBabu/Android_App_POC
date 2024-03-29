package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.BaseApplication
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.R
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.fragments.auth.AuthNavHostFragment
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.BaseActivity
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth.state.AuthStateEvent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.MainActivity
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.StateMessageCallback
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.SuccessHandling.Companion.RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class AuthActivity : BaseActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory

    val viewModel: AuthViewModel by viewModels {
        providerFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        subscribeObservers()
        onRestoreInstanceState()
        viewModel.setupChannel()
    }

    fun onRestoreInstanceState() {
        val host = supportFragmentManager.findFragmentById(R.id.auth_fragments_container)
        host?.let {
            // do nothing
        } ?: createNavHost()
    }

    private fun createNavHost() {
        val navHost = AuthNavHostFragment.create(
            R.navigation.auth_nav_graph
        )
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.auth_fragments_container,
                navHost,
                getString(R.string.AuthNavHost)
            )
            .setPrimaryNavigationFragment(navHost)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        checkPreviousAuthUser()
    }

    private fun subscribeObservers() {

        viewModel.viewState.observe(this, Observer { viewState ->
            Log.d(TAG, "AuthActivity, subscribeObservers: AuthViewState: ${viewState}")
            viewState.authToken?.let {
                sessionManager.login(it)
            }
        })

        viewModel.numActiveJobs.observe(this, Observer { jobCounter ->
            Log.d(TAG, "active jobs: ${jobCounter}")
            displayProgressBar(viewModel.areAnyJobsActive())
        })

        viewModel.stateMessage.observe(this, Observer { stateMessage ->

            stateMessage?.let {
                if (it.response.message.equals(RESPONSE_CHECK_PREVIOUS_AUTH_USER_DONE)) {
                    onFinishCheckPreviousAuthUser()
                }
                onResponseReceived(
                    response = it.response,
                    stateMessageCallback = object : StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModel.clearStateMessage()
                        }
                    }
                )
            }
        })

        sessionManager.cachedToken.observe(this, Observer { dataState ->
            Log.d(TAG, "AuthActivity, subscribeObservers: AuthDataState: ${dataState}")
            dataState.let { authToken ->
                if (authToken != null && authToken.account_pk != -1 && authToken.token != null) {
                    navMainActivity()
                }
            }
        })
    }

    fun navMainActivity() {
        Log.d(TAG, "navMainActivity: called.")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        (application as BaseApplication).releaseAuthComponent()
    }

    private fun checkPreviousAuthUser() {
        viewModel.setStateEvent(AuthStateEvent.CheckPreviousAuthEvent())
    }

    private fun onFinishCheckPreviousAuthUser() {
        fragment_container.visibility = View.VISIBLE
    }

    override fun inject() {
        (application as BaseApplication).authComponent()
            .inject(this)
    }

    override fun displayProgressBar(isLoading: Boolean) {
        if (isLoading) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    override fun expandAppBar() {
        // ignore
    }


}

