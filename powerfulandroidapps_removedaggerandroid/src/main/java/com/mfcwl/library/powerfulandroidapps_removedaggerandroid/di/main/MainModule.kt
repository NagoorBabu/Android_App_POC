package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.main

import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.api.main.OpenApiMainService
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.persistance.AccountPropertiesDao
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.persistance.AppDatabase
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.persistance.BlogPostDao
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.respository.main.AccountRepository
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.respository.main.BlogRepository
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.respository.main.CreateBlogRepository
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object MainModule {

    @JvmStatic
    @MainScope
    @Provides
    fun provideOpenApiMainService(retrofitBuilder: Retrofit.Builder): OpenApiMainService {
        return retrofitBuilder
            .build()
            .create(OpenApiMainService::class.java)
    }

    @JvmStatic
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

    @JvmStatic
    @MainScope
    @Provides
    fun provideBlogPostDao(db: AppDatabase): BlogPostDao {
        return db.getBlogPostDao()
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideBlogRepository(
        openApiMainService: OpenApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ): BlogRepository {
        return BlogRepository(openApiMainService, blogPostDao, sessionManager)
    }

    @JvmStatic
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