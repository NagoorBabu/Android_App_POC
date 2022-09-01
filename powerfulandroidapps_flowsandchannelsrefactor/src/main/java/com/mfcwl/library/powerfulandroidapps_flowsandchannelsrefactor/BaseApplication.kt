package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor

import android.app.Application
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.AppComponent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.DaggerAppComponent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.auth.AuthComponent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main.MainComponent

class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    private var authComponent: AuthComponent? = null

    private var mainComponent: MainComponent? = null

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    fun releaseMainComponent() {
        mainComponent = null
    }

    fun mainComponent(): MainComponent {
        if (mainComponent == null) {
            mainComponent = appComponent.mainComponent().create()
        }
        return mainComponent as MainComponent
    }

    fun releaseAuthComponent() {
        authComponent = null
    }

    fun authComponent(): AuthComponent {
        if (authComponent == null) {
            authComponent = appComponent.authComponent().create()
        }
        return authComponent as AuthComponent
    }

    fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}