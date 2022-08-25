package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di

import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.auth.AuthFragmentBuildersModule
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.auth.AuthModule
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.auth.AuthScope
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.auth.AuthViewModelModule
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.main.MainFragmentBuildersModule
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.main.MainModule
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.main.MainScope
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.main.MainViewModelModule
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.AuthActivity
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainFragmentBuildersModule::class, MainViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}