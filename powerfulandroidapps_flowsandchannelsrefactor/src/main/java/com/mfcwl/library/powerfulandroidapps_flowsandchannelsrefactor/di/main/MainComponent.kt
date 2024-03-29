package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main

import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.MainActivity
import dagger.Subcomponent

@MainScope
@Subcomponent(
    modules = [
        MainModule::class,
        MainViewModelModule::class,
        MainFragmentsModule::class
    ]
)
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): MainComponent
    }

    fun inject(mainActivity: MainActivity)

}