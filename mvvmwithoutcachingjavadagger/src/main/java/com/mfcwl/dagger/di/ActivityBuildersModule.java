package com.mfcwl.dagger.di;

import com.mfcwl.dagger.di.auth.AuthModule;
import com.mfcwl.dagger.di.auth.AuthScope;
import com.mfcwl.dagger.di.auth.AuthViewModelsModule;
import com.mfcwl.dagger.di.main.MainFragmentBuildersModule;
import com.mfcwl.dagger.di.main.MainModule;
import com.mfcwl.dagger.di.main.MainScope;
import com.mfcwl.dagger.di.main.MainViewModelsModule;
import com.mfcwl.dagger.ui.auth.AuthActivity;
import com.mfcwl.dagger.ui.auth.AuthViewModel;
import com.mfcwl.dagger.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();
}
