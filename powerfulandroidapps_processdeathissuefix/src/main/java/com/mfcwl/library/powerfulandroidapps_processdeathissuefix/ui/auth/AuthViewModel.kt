package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth

import androidx.lifecycle.LiveData
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.models.AuthToken
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.respository.auth.AuthRepository
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.BaseViewModel
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.DataState
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.state.AuthStateEvent
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.state.AuthViewState
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.state.LoginFields
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.state.RegistrationFields
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
) : BaseViewModel<AuthStateEvent, AuthViewState>() {

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when (stateEvent) {
            is AuthStateEvent.LoginAttemptEvent -> {
                return authRepository.attemptLogin(
                    stateEvent.email,
                    stateEvent.password
                )
            }

            is AuthStateEvent.RegisterAttemptEvent -> {
                return authRepository.attemptRegistration(
                    stateEvent.email,
                    stateEvent.username,
                    stateEvent.password,
                    stateEvent.confirm_password
                )
            }

            is AuthStateEvent.CheckPreviousAuthEvent -> {
                return authRepository.checkPreviousAuthUser()
            }

            is AuthStateEvent.None ->{
                return object: LiveData<DataState<AuthViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        value = DataState.data(null, null)
                    }
                }
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
        setViewState(update)
    }

    fun setLoginFields(loginFields: LoginFields) {
        val update = getCurrentViewStateOrNew()
        if (update.loginFields == loginFields) {
            return
        }
        update.loginFields = loginFields
        setViewState(update)
    }

    fun setAuthToken(authToken: AuthToken) {
        val update = getCurrentViewStateOrNew()
        if (update.authToken == authToken) {
            return
        }
        update.authToken = authToken
        setViewState(update)
    }

    fun cancelActiveJobs(){
        handlePendingData()
        authRepository.cancelActiveJobs()
    }

    fun handlePendingData(){
        setStateEvent(AuthStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}