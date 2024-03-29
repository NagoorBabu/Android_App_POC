package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.auth

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.fragments.auth.AuthFragmentFactory
import dagger.Module
import dagger.Provides

@Module
object AuthFragmentsModule {

    @JvmStatic
    @AuthScope
    @Provides
    fun provideFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory
    ): FragmentFactory {
        return AuthFragmentFactory(
            viewModelFactory
        )
    }

}