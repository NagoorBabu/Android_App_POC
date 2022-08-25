package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account

import androidx.lifecycle.LiveData
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.models.AccountProperties
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.respository.main.AccountRepository
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.session.SessionManager
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.BaseViewModel
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.DataState
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.Loading
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.state.AccountStateEvent
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.state.AccountViewState
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.util.AbsentLiveData
import javax.inject.Inject

class AccountViewModel
@Inject
constructor(
    val sessionManager: SessionManager,
    val accountRepository: AccountRepository
) : BaseViewModel<AccountStateEvent, AccountViewState>() {

    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        when (stateEvent) {

            is AccountStateEvent.GetAccountPropertiesEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    accountRepository.getAccountProperties(authToken)
                } ?: AbsentLiveData.create()
            }

            is AccountStateEvent.UpdateAccountPropertiesEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    authToken.account_pk?.let { pk ->
                        val newAccountProperties = AccountProperties(
                            pk,
                            stateEvent.email,
                            stateEvent.username
                        )
                        accountRepository.saveAccountProperties(
                            authToken,
                            newAccountProperties
                        )
                    }
                } ?: AbsentLiveData.create()
            }

            is AccountStateEvent.ChangePasswordEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    accountRepository.updatePassword(
                        authToken,
                        stateEvent.currentPassword,
                        stateEvent.newPassword,
                        stateEvent.confirmNewPassword
                    )
                } ?: AbsentLiveData.create()
            }

            is AccountStateEvent.None ->{
                return object: LiveData<DataState<AccountViewState>>(){
                    override fun onActive() {
                        super.onActive()
                        value = DataState(null, Loading(false), null)
                    }
                }
            }
        }
    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties) {
        val update = getCurrentViewStateOrNew()
        if (update.accountProperties == accountProperties) {
            return
        }
        update.accountProperties = accountProperties
        setViewState(update)
    }

    fun logout() {
        sessionManager.logout()
    }

    fun cancelActiveJobs(){
        accountRepository.cancelActiveJobs() // cancel active jobs
        handlePendingData() // hide progress bar
    }

    fun handlePendingData(){
        setStateEvent(AccountStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}