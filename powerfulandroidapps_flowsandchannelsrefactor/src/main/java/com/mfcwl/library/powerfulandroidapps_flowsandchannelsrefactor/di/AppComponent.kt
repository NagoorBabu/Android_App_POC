package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di

import android.app.Application
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.auth.AuthComponent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main.MainComponent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.session.SessionManager
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.BaseActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        SubComponentsModule::class
    ]
)
interface AppComponent {

    val sessionManager: SessionManager

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(baseActivity: BaseActivity)

    fun authComponent(): AuthComponent.Factory

    fun mainComponent(): MainComponent.Factory

}
