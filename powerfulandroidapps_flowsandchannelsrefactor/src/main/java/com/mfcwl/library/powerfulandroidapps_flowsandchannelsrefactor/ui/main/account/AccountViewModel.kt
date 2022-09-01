package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.account

import android.util.Log
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main.MainScope
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.models.AccountProperties
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.main.AccountRepositoryImpl
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.session.SessionManager
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.BaseViewModel
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.account.state.AccountStateEvent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.account.state.AccountViewState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.*
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.ErrorHandling.Companion.INVALID_STATE_EVENT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@MainScope
class AccountViewModel
@Inject
constructor(
    val sessionManager: SessionManager,
    val accountRepository: AccountRepositoryImpl
) : BaseViewModel<AccountViewState>() {

    override fun handleNewData(stateEvent: StateEvent?, data: AccountViewState) {

        Log.d(TAG, "handleNewData: ${data}")

        data.accountProperties?.let { accountProperties ->
            setAccountPropertiesData(accountProperties)
        }

        _activeStateEventTracker.removeStateEvent(stateEvent)
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        sessionManager.cachedToken.value?.let { authToken ->
            val job: Flow<DataState<AccountViewState>> = when (stateEvent) {

                is AccountStateEvent.GetAccountPropertiesEvent -> {
                    accountRepository.getAccountProperties(
                        stateEvent = stateEvent,
                        authToken = authToken
                    )
                }

                is AccountStateEvent.UpdateAccountPropertiesEvent -> {
                    accountRepository.saveAccountProperties(
                        stateEvent = stateEvent,
                        authToken = authToken,
                        email = stateEvent.email,
                        username = stateEvent.username
                    )
                }

                is AccountStateEvent.ChangePasswordEvent -> {
                    accountRepository.updatePassword(
                        stateEvent = stateEvent,
                        authToken = authToken,
                        currentPassword = stateEvent.currentPassword,
                        newPassword = stateEvent.newPassword,
                        confirmNewPassword = stateEvent.confirmNewPassword
                    )
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
        } ?: sessionManager.logout()
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties) {
        val update = getCurrentViewStateOrNew()
        if (update.accountProperties == accountProperties) {
            return
        }
        update.accountProperties = accountProperties
        setViewState(update)
    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }

    fun logout() {
        sessionManager.logout()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}

