package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.main

import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main.MainScope
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.models.AuthToken
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.models.BlogPost
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.blog.state.BlogViewState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.DataState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.StateEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

@FlowPreview
@MainScope
interface BlogRepository {

    fun searchBlogPosts(
        authToken: AuthToken,
        query: String,
        filterAndOrder: String,
        page: Int,
        stateEvent: StateEvent
    ): Flow<DataState<BlogViewState>>

    fun restoreBlogListFromCache(
        query: String,
        filterAndOrder: String,
        page: Int,
        stateEvent: StateEvent
    ): Flow<DataState<BlogViewState>>

    fun isAuthorOfBlogPost(
        authToken: AuthToken,
        slug: String,
        stateEvent: StateEvent
    ): Flow<DataState<BlogViewState>>

    fun deleteBlogPost(
        authToken: AuthToken,
        blogPost: BlogPost,
        stateEvent: StateEvent
    ): Flow<DataState<BlogViewState>>

    fun updateBlogPost(
        authToken: AuthToken,
        slug: String,
        title: RequestBody,
        body: RequestBody,
        image: MultipartBody.Part?,
        stateEvent: StateEvent
    ): Flow<DataState<BlogViewState>>


}


