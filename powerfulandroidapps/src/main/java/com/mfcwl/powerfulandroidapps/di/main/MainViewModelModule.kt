package com.mfcwl.powerfulandroidapps.di.main

import androidx.lifecycle.ViewModel
import com.mfcwl.powerfulandroidapps.di.ViewModelKey
import com.mfcwl.powerfulandroidapps.ui.main.account.AccountViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

}