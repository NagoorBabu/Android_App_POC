package com.mfcwl.app.mviarchitecture.repository.main

import androidx.lifecycle.LiveData
import com.mfcwl.app.mviarchitecture.api.RetrofitBuilder
import com.mfcwl.app.mviarchitecture.model.BlogPost
import com.mfcwl.app.mviarchitecture.model.User
import com.mfcwl.app.mviarchitecture.repository.NetworkBoundResource
import com.mfcwl.app.mviarchitecture.ui.main.state.MainViewState
import com.mfcwl.app.mviarchitecture.util.ApiSuccessResponse
import com.mfcwl.app.mviarchitecture.util.DataState
import com.mfcwl.app.mviarchitecture.util.GenericApiResponse

object MainRepository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        blogposts = response.body,
                        user = null
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()

    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        blogposts = null,
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()

    }
}