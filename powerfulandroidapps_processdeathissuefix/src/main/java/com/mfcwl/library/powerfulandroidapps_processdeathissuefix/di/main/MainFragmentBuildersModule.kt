package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.di.main

import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.AccountFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.ChangePasswordFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.UpdateAccountFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.BlogFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.UpdateBlogFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.ViewBlogFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.create_blog.CreateBlogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeBlogFragment(): BlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector()
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreateBlogFragment(): CreateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateBlogFragment(): UpdateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeViewBlogFragment(): ViewBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateAccountFragment(): UpdateAccountFragment
}