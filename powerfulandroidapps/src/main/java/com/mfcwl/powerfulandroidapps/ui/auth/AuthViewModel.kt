package com.mfcwl.powerfulandroidapps.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mfcwl.powerfulandroidapps.api.auth.network_responses.LoginResponse
import com.mfcwl.powerfulandroidapps.api.auth.network_responses.RegistrationResponse
import com.mfcwl.powerfulandroidapps.models.AuthToken
import com.mfcwl.powerfulandroidapps.respository.auth.AuthRepository
import com.mfcwl.powerfulandroidapps.ui.BaseViewModel
import com.mfcwl.powerfulandroidapps.ui.DataState
import com.mfcwl.powerfulandroidapps.ui.auth.state.AuthStateEvent
import com.mfcwl.powerfulandroidapps.ui.auth.state.AuthStateEvent.*
import com.mfcwl.powerfulandroidapps.ui.auth.state.AuthViewState
import com.mfcwl.powerfulandroidapps.ui.auth.state.LoginFields
import com.mfcwl.powerfulandroidapps.ui.auth.state.RegistrationFields
import com.mfcwl.powerfulandroidapps.util.AbsentLiveData
import com.mfcwl.powerfulandroidapps.util.GenericApiResponse
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
) : BaseViewModel<AuthStateEvent, AuthViewState>() {

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when (stateEvent) {
            is LoginAttemptEvent -> {
                return authRepository.attemptLogin(
                    stateEvent.email,
                    stateEvent.password
                )
            }

            is RegisterAttemptEvent -> {
                return authRepository.attemptRegistration(
                    stateEvent.email,
                    stateEvent.username,
                    stateEvent.password,
                    stateEvent.confirm_password
                )
            }

            is CheckPreviousAuthEvent -> {
                return AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }

    fun setRegistrationFields(registrationFields: RegistrationFields) {
        val update = getCurrentViewStateOrNew()
        if (update.registrationFields == registrationFields) {
            return
        }
        update.registrationFields = registrationFields
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields) {
        val update = getCurrentViewStateOrNew()
        if (update.loginFields == loginFields) {
            return
        }
        update.loginFields = loginFields
        _viewState.value = update
    }

    fun setAuthToken(authToken: AuthToken) {
        val update = getCurrentViewStateOrNew()
        if (update.authToken == authToken) {
            return
        }
        update.authToken = authToken
        _viewState.value = update
    }
}