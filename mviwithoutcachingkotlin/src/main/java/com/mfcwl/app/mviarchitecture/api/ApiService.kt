package com.mfcwl.app.mviarchitecture.api

import androidx.lifecycle.LiveData
import com.mfcwl.app.mviarchitecture.model.BlogPost
import com.mfcwl.app.mviarchitecture.model.User
import com.mfcwl.app.mviarchitecture.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>
}