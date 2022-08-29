package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.main

import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.MainActivity
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