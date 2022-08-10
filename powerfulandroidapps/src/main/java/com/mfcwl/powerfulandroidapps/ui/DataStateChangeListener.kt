package com.mfcwl.powerfulandroidapps.ui

interface DataStateChangeListener {

    fun onDataStateChange(dataState: DataState<*>?)

    fun expandAppBar()

    fun hideSoftKeyboard()
}