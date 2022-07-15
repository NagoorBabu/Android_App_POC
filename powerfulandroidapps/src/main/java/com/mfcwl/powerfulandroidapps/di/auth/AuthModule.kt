package com.mfcwl.powerfulandroidapps.di.auth

import com.mfcwl.powerfulandroidapps.api.auth.OpenApiAuthService
import com.mfcwl.powerfulandroidapps.persistance.AccountPropertiesDao
import com.mfcwl.powerfulandroidapps.persistance.AuthTokenDao
import com.mfcwl.powerfulandroidapps.respository.auth.AuthRepository
import com.mfcwl.powerfulandroidapps.session.SessionManager
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
        openApiAuthService: OpenApiAuthService
    ): AuthRepository {
        return AuthRepository(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager
        )
    }

}