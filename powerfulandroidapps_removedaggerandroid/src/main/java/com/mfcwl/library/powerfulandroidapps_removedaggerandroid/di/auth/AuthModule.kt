package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.auth

import android.content.SharedPreferences
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.api.auth.OpenApiAuthService
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.persistance.AccountPropertiesDao
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.persistance.AuthTokenDao
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.respository.auth.AuthRepository
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

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