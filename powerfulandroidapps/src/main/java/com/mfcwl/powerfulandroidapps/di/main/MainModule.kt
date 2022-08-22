package com.mfcwl.powerfulandroidapps.di.main

import com.mfcwl.powerfulandroidapps.api.main.OpenApiMainService
import com.mfcwl.powerfulandroidapps.persistance.AccountPropertiesDao
import com.mfcwl.powerfulandroidapps.persistance.AppDatabase
import com.mfcwl.powerfulandroidapps.persistance.BlogPostDao
import com.mfcwl.powerfulandroidapps.respository.main.AccountRepository
import com.mfcwl.powerfulandroidapps.respository.main.BlogRepository
import com.mfcwl.powerfulandroidapps.respository.main.CreateBlogRepository
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