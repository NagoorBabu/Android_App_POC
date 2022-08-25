package com.mfcwl.library.powerfulandroidapps_processdeathissuefix

import android.app.Activity
import android.app.Application
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.AppInjector
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

}