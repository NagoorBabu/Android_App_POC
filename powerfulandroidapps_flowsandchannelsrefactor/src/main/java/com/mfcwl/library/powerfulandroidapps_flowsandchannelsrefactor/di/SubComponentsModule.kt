package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di

import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.auth.AuthComponent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        AuthComponent::class,
        MainComponent::class
    ]
)
class SubComponentsModule