package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.auth.keys.MainViewModelKey
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.account.AccountViewModel
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.blog.viewmodel.BlogViewModel
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.create_blog.CreateBlogViewModel
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.viewmodels.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @MainScope
    @Binds
    abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(BlogViewModel::class)
    abstract fun bindBlogViewModel(blogViewModel: BlogViewModel): ViewModel

    @MainScope
    @Binds
    @IntoMap
    @MainViewModelKey(CreateBlogViewModel::class)
    abstract fun bindCreateBlogViewModel(createBlogViewModel: CreateBlogViewModel): ViewModel
}