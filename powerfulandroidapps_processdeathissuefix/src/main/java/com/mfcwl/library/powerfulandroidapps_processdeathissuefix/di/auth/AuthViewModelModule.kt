package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.auth

import androidx.lifecycle.ViewModel
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.ViewModelKey
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.AuthViewModel
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