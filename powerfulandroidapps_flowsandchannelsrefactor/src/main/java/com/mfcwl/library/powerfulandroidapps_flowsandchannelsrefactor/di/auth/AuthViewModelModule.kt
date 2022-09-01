package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.auth.keys.AuthViewModelKey
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.auth.AuthViewModel
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.viewmodels.AuthViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @AuthScope
    @Binds
    abstract fun bindViewModelFactory(factory: AuthViewModelFactory): ViewModelProvider.Factory

    @AuthScope
    @Binds
    @IntoMap
    @AuthViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}