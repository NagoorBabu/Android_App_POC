package com.mfcwl.powerfulandroidapps.respository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.mfcwl.powerfulandroidapps.api.auth.OpenApiAuthService
import com.mfcwl.powerfulandroidapps.api.auth.network_responses.LoginResponse
import com.mfcwl.powerfulandroidapps.api.auth.network_responses.RegistrationResponse
import com.mfcwl.powerfulandroidapps.models.AuthToken
import com.mfcwl.powerfulandroidapps.persistance.AccountPropertiesDao
import com.mfcwl.powerfulandroidapps.persistance.AuthTokenDao
import com.mfcwl.powerfulandroidapps.session.SessionManager
import com.mfcwl.powerfulandroidapps.ui.DataState
import com.mfcwl.powerfulandroidapps.ui.Response
import com.mfcwl.powerfulandroidapps.ui.ResponseType
import com.mfcwl.powerfulandroidapps.ui.auth.state.AuthViewState
import com.mfcwl.powerfulandroidapps.util.ApiEmptyResponse
import com.mfcwl.powerfulandroidapps.util.ApiErrorResponse
import com.mfcwl.powerfulandroidapps.util.ApiSuccessResponse
import com.mfcwl.powerfulandroidapps.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.mfcwl.powerfulandroidapps.util.GenericApiResponse
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    val authTokenDao: AuthTokenDao,
    val accountPropertiesDao: AccountPropertiesDao,
    val openApiAuthService: OpenApiAuthService,
    val sessionManager: SessionManager
) {

    fun attemptLogin(email: String, password: String): LiveData<DataState<AuthViewState>> {
        return openApiAuthService.login(email, password)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (response) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = AuthViewState(
                                        authToken = AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ),
                                    response = null
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }

    fun attemptRegistration(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): LiveData<DataState<AuthViewState>> {
        return openApiAuthService.register(email, username, password, confirmPassword)
            .switchMap { response ->
                object : LiveData<DataState<AuthViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (response) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = AuthViewState(
                                        authToken = AuthToken(
                                            response.body.pk,
                                            response.body.token
                                        )
                                    ),
                                    response = null
                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = response.errorMessage,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    response = Response(
                                        message = ERROR_UNKNOWN,
                                        responseType = ResponseType.Dialog()
                                    )
                                )
                            }
                        }
                    }
                }
            }
    }
}