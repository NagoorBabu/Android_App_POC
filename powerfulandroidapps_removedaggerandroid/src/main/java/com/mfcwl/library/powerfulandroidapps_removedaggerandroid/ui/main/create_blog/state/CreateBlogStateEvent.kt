package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.create_blog.state

import okhttp3.MultipartBody

sealed class CreateBlogStateEvent {

    data class CreateNewBlogEvent(
        val title: String,
        val body: String,
        val image: MultipartBody.Part
    ): CreateBlogStateEvent()

    class None: CreateBlogStateEvent()
}