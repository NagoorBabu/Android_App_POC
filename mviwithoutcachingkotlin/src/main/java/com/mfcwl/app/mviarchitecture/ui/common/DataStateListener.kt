package com.mfcwl.app.mviarchitecture.ui.common

import com.mfcwl.app.mviarchitecture.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}