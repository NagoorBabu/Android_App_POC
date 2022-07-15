package com.mfcwl.app.mvvmbasic.data.api

import com.mfcwl.app.mvvmbasic.data.model.User
import io.reactivex.Single

interface ApiService {
    fun getUsers(): Single<List<User>>
}