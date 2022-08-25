package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.main

import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.api.main.OpenApiMainService
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.persistance.AccountPropertiesDao
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.persistance.AppDatabase
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.persistance.BlogPostDao
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.respository.main.AccountRepository
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.respository.main.BlogRepository
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.respository.main.CreateBlogRepository
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.session.SessionManager
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

    @MainScope
    @Provides
    fun provideBlogPostDao(db: AppDatabase): BlogPostDao {
        return db.getBlogPostDao()
    }

    @MainScope
    @Provides
    fun provideBlogRepository(
        openApiMainService: OpenApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ): BlogRepository {
        return BlogRepository(openApiMainService, blogPostDao, sessionManager)
    }

    @MainScope
    @Provides
    fun provideCreateBlogRepository(
        openApiMainService: OpenApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ): CreateBlogRepository {
        return CreateBlogRepository(openApiMainService, blogPostDao, sessionManager)
    }
}