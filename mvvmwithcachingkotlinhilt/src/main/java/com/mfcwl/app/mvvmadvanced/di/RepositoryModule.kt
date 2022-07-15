package com.mfcwl.app.mvvmadvanced.di

import com.mfcwl.app.mvvmadvanced.repository.MainRepository
import com.mfcwl.app.mvvmadvanced.retrofit.BlogRetrofit
import com.mfcwl.app.mvvmadvanced.retrofit.NetworkMapper
import com.mfcwl.app.mvvmadvanced.room.BlogDao
import com.mfcwl.app.mvvmadvanced.room.CacheMapper
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
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}