package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui

import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.Response
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.StateMessageCallback

interface UICommunicationListener {

    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )

    fun displayProgressBar(isLoading: Boolean)

    fun expandAppBar()

    fun hideSoftKeyboard()

    fun isStoragePermissionGranted(): Boolean
}