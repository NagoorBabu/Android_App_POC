package com.mfcwl.powerfulandroidapps.di

import androidx.lifecycle.ViewModelProvider
import com.mfcwl.powerfulandroidapps.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}