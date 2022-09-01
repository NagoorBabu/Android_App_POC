package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main

import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.api.main.OpenApiMainService
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.persistance.AccountPropertiesDao
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.persistance.AppDatabase
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.persistance.BlogPostDao
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.main.*
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.session.SessionManager
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit

@FlowPreview
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
        return AccountRepositoryImpl(
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
        return BlogRepositoryImpl(openApiMainService, blogPostDao, sessionManager)
    }

    @JvmStatic
    @MainScope
    @Provides
    fun provideCreateBlogRepository(
        openApiMainService: OpenApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ): CreateBlogRepository {
        return CreateBlogRepositoryImpl(openApiMainService, blogPostDao, sessionManager)
    }
}