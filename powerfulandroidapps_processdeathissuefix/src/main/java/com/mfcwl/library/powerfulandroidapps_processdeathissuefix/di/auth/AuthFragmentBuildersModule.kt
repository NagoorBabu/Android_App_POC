package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.auth

import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.ForgotPasswordFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.LauncherFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.LoginFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLauncherFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgotPasswordFragment

}