package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di

import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.auth.AuthComponent
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.main.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        AuthComponent::class,
        MainComponent::class
    ]
)
class SubComponentsModule