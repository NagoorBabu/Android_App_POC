package com.mfcwl.powerfulandroidapps.di.auth

import com.mfcwl.powerfulandroidapps.ui.auth.ForgotPasswordFragment
import com.mfcwl.powerfulandroidapps.ui.auth.LauncherFragment
import com.mfcwl.powerfulandroidapps.ui.auth.LoginFragment
import com.mfcwl.powerfulandroidapps.ui.auth.RegisterFragment
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