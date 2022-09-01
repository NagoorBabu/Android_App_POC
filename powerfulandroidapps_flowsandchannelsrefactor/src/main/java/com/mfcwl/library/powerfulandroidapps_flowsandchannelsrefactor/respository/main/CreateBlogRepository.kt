package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.main

import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main.MainScope
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.models.AuthToken
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.create_blog.state.CreateBlogViewState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.DataState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.StateEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

@FlowPreview
@MainScope
interface CreateBlogRepository {

    fun createNewBlogPost(
        authToken: AuthToken,
        title: RequestBody,
        body: RequestBody,
        image: MultipartBody.Part?,
        stateEvent: StateEvent
    ): Flow<DataState<CreateBlogViewState>>
}