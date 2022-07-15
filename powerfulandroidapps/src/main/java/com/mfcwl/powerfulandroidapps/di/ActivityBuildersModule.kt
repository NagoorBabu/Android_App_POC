package com.mfcwl.powerfulandroidapps.di

import com.mfcwl.powerfulandroidapps.di.auth.AuthFragmentBuildersModule
import com.mfcwl.powerfulandroidapps.di.auth.AuthModule
import com.mfcwl.powerfulandroidapps.di.auth.AuthScope
import com.mfcwl.powerfulandroidapps.di.auth.AuthViewModelModule
import com.mfcwl.powerfulandroidapps.ui.auth.AuthActivity
import com.mfcwl.powerfulandroidapps.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}