package com.mfcwl.dagger.di.auth;

import androidx.lifecycle.ViewModel;

import com.mfcwl.dagger.di.ViewModelKey;
import com.mfcwl.dagger.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
