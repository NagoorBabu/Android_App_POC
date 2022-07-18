package com.mfcwl.powerfulandroidapps.di.main

import com.mfcwl.powerfulandroidapps.ui.main.account.AccountFragment
import com.mfcwl.powerfulandroidapps.ui.main.account.ChangePasswordFragment
import com.mfcwl.powerfulandroidapps.ui.main.account.UpdateAccountFragment
import com.mfcwl.powerfulandroidapps.ui.main.blog.BlogFragment
import com.mfcwl.powerfulandroidapps.ui.main.blog.UpdateBlogFragment
import com.mfcwl.powerfulandroidapps.ui.main.blog.ViewBlogFragment
import com.mfcwl.powerfulandroidapps.ui.main.create_blog.CreateBlogFragment
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