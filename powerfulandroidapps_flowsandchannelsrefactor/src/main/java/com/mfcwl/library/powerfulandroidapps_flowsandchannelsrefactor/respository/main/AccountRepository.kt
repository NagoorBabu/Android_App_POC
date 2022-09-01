package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.main

import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main.MainScope
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.models.AuthToken
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.account.state.AccountViewState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.DataState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.StateEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
@MainScope
interface AccountRepository {

    fun getAccountProperties(
        authToken: AuthToken,
        stateEvent: StateEvent
    ): Flow<DataState<AccountViewState>>

    fun saveAccountProperties(
        authToken: AuthToken,
        email: String,
        username: String,
        stateEvent: StateEvent
    ): Flow<DataState<AccountViewState>>

    fun updatePassword(
        authToken: AuthToken,
        currentPassword: String,
        newPassword: String,
        confirmNewPassword: String,
        stateEvent: StateEvent
    ): Flow<DataState<AccountViewState>>
}