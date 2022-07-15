package com.app.cameraxwork.mvccamera.retrofit

import com.app.cameraxwork.mvccamera.retrofit.lprnetworkentity.LprNetworkEntity
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface LprRetrofit {

    @Multipart
    @Headers("Authorization: Token 8295c0dc04516466a496a8741f13f1d83058ab54")
    @POST("v1/plate-reader/")
    suspend fun get(@Part imageFile: MultipartBody.Part): LprNetworkEntity
}