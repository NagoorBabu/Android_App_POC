package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.R
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.account.state.ACCOUNT_VIEW_STATE_BUNDLE_KEY
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.account.state.AccountStateEvent
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.account.state.AccountViewState
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.util.SuccessHandling.Companion.RESPONSE_PASSWORD_UPDATE_SUCCESS
import kotlinx.android.synthetic.main.fragment_change_password.*
import javax.inject.Inject

class ChangePasswordFragment
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): BaseAccountFragment(R.layout.fragment_change_password) {

    val viewModel: AccountViewModel by viewModels{
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cancelActiveJobs()
        // Restore state after process death
        savedInstanceState?.let { inState ->
            (inState[ACCOUNT_VIEW_STATE_BUNDLE_KEY] as AccountViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }
        }
    }

    override fun cancelActiveJobs(){
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        update_password_button.setOnClickListener {
            viewModel.setStateEvent(
                AccountStateEvent.ChangePasswordEvent(
                    input_current_password.text.toString(),
                    input_new_password.text.toString(),
                    input_confirm_new_password.text.toString()
                )
            )
        }

        subscribeObservers()
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer{ dataState ->
            stateChangeListener.onDataStateChange(dataState)
            Log.d(TAG, "ChangePasswordFragment, DataState: ${dataState}")
            if(dataState != null){
                dataState.data?.let { data ->
                    data.response?.let{ event ->
                        if(event.peekContent()
                                .message
                                .equals(RESPONSE_PASSWORD_UPDATE_SUCCESS)
                        ){
                            stateChangeListener.hideSoftKeyboard()
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        })
    }
}
