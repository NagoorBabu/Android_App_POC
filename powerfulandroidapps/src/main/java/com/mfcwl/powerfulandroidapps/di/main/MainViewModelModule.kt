package com.mfcwl.powerfulandroidapps.di.main

import androidx.lifecycle.ViewModel
import com.mfcwl.powerfulandroidapps.di.ViewModelKey
import com.mfcwl.powerfulandroidapps.ui.main.account.AccountViewModel
import com.mfcwl.powerfulandroidapps.ui.main.blog.viewmodel.BlogViewModel
import com.mfcwl.powerfulandroidapps.ui.main.create_blog.CreateBlogViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BlogViewModel::class)
    abstract fun bindBlogViewModel(blogViewModel: BlogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateBlogViewModel::class)
    abstract fun bindCreateBlogViewModel(createBlogViewModel: CreateBlogViewModel): ViewModel
}