package com.app.cameraxwork.mvccamera.di

import com.app.cameraxwork.mvccamera.repository.LprRepository
import com.app.cameraxwork.mvccamera.retrofit.LprRetrofit
import com.app.cameraxwork.mvccamera.room.CacheMapper
import com.app.cameraxwork.mvccamera.room.LprDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        dao: LprDao,
        retrofit: LprRetrofit,
        cacheMapper: CacheMapper
    ): LprRepository {
        return LprRepository(dao, retrofit, cacheMapper)
    }
}