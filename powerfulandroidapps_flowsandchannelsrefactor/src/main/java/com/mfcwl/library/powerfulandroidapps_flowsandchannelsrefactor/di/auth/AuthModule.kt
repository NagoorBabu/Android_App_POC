package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.auth

import android.content.SharedPreferences
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.api.auth.OpenApiAuthService
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.persistance.AccountPropertiesDao
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.persistance.AuthTokenDao
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.auth.AuthRepository
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.auth.AuthRepositoryImpl
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.session.SessionManager
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit

@FlowPreview
@Module
object AuthModule {

    @JvmStatic
    @AuthScope
    @Provides
    fun provideOpenApiAuthService(retrofitBuilder: Retrofit.Builder): OpenApiAuthService {
        return retrofitBuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }

    @JvmStatic
    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: OpenApiAuthService,
        preferences: SharedPreferences,
        editor: SharedPreferences.Editor
    ): AuthRepository {
        return AuthRepositoryImpl(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager,
            preferences,
            editor
        )
    }

}