package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.auth

import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.auth.AuthScope
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth.state.AuthViewState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.DataState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.StateEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
@AuthScope
interface AuthRepository {

    fun attemptLogin(
        stateEvent: StateEvent,
        email: String,
        password: String
    ): Flow<DataState<AuthViewState>>

    fun attemptRegistration(
        stateEvent: StateEvent,
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): Flow<DataState<AuthViewState>>

    fun checkPreviousAuthUser(
        stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>>

    fun saveAuthenticatedUserToPrefs(email: String)

    fun returnNoTokenFound(
        stateEvent: StateEvent
    ): DataState<AuthViewState>

}