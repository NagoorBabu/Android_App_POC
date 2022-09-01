package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth

import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.auth.AuthScope
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.models.AuthToken
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.auth.AuthRepository
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.BaseViewModel
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth.state.AuthStateEvent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth.state.AuthViewState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth.state.LoginFields
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth.state.RegistrationFields
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.*
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.ErrorHandling.Companion.INVALID_STATE_EVENT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@AuthScope
class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
) : BaseViewModel<AuthViewState>() {

    override fun handleNewData(stateEvent: StateEvent?, data: AuthViewState) {

        data.authToken?.let { authToken ->
            setAuthToken(authToken)
        }

        _activeStateEventTracker.removeStateEvent(stateEvent)
    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<AuthViewState>> = when (stateEvent) {

            is AuthStateEvent.LoginAttemptEvent -> {
                authRepository.attemptLogin(
                    stateEvent = stateEvent,
                    email = stateEvent.email,
                    password = stateEvent.password
                )
            }

            is AuthStateEvent.RegisterAttemptEvent -> {
                authRepository.attemptRegistration(
                    stateEvent = stateEvent,
                    email = stateEvent.email,
                    username = stateEvent.username,
                    password = stateEvent.password,
                    confirmPassword = stateEvent.confirm_password
                )
            }

            is AuthStateEvent.CheckPreviousAuthEvent -> {
                authRepository.checkPreviousAuthUser(stateEvent)
            }

            else -> {
                flow {
                    emit(
                        DataState.error(
                            response = Response(
                                message = INVALID_STATE_EVENT,
                                uiComponentType = UIComponentType.None(),
                                messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                        )
                    )
                }
            }
        }
        launchJob(stateEvent, job)
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

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }


}
