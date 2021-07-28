package com.mfcwl.app.mvvmbasic.data.repository

import com.mfcwl.app.mvvmbasic.data.api.ApiHelper
import com.mfcwl.app.mvvmbasic.data.model.User
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers(): Single<List<User>>{
        return apiHelper.getUsers()
    }
}