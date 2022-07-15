package com.app.cameraxwork.mvccamera.di

import com.app.cameraxwork.mvccamera.retrofit.LprRetrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl("https://api.platerecognizer.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())

    }

    @Singleton
    @Provides
    fun provideLprService(retrofit: Retrofit.Builder): LprRetrofit {
        return retrofit
            .build()
            .create(LprRetrofit::class.java)
    }
}