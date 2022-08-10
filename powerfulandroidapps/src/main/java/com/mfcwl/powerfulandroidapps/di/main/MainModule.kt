package com.mfcwl.powerfulandroidapps.di.main

import com.mfcwl.powerfulandroidapps.api.main.OpenApiMainService
import com.mfcwl.powerfulandroidapps.persistance.AccountPropertiesDao
import com.mfcwl.powerfulandroidapps.respository.main.AccountRepository
import com.mfcwl.powerfulandroidapps.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideOpenApiMainService(retrofitBuilder: Retrofit.Builder): OpenApiMainService {
        return retrofitBuilder
            .build()
            .create(OpenApiMainService::class.java)
    }

    @MainScope
    @Provides
    fun provideAccountRepository(
        openApiMainService: OpenApiMainService,
        accountPropertiesDao: AccountPropertiesDao,
        sessionManager: SessionManager
    ): AccountRepository {
        return AccountRepository(
            openApiMainService,
            accountPropertiesDao,
            sessionManager
        )
    }
}