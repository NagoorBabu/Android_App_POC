package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di

import androidx.lifecycle.ViewModelProvider
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}