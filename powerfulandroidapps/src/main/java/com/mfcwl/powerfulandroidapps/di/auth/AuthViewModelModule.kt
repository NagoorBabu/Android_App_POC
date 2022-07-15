package com.mfcwl.powerfulandroidapps.di.auth

import androidx.lifecycle.ViewModel
import com.mfcwl.powerfulandroidapps.di.ViewModelKey
import com.mfcwl.powerfulandroidapps.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}