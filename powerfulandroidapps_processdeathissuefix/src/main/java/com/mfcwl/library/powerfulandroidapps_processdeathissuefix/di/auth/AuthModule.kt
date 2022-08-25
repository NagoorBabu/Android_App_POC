package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.auth

import android.content.SharedPreferences
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.api.auth.OpenApiAuthService
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.persistance.AccountPropertiesDao
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.persistance.AuthTokenDao
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.respository.auth.AuthRepository
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideOpenApiAuthService(retrofitBuilder: Retrofit.Builder): OpenApiAuthService {
        return retrofitBuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }

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
        return AuthRepository(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager,
            preferences,
            editor
        )
    }

}