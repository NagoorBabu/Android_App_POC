package com.mfcwl.library.powerfulandroidapps_removedaggerandroid.respository.main

import androidx.lifecycle.LiveData
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.api.main.OpenApiMainService
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.api.main.responses.BlogCreateUpdateResponse
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.auth.AuthScope
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.di.main.MainScope
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.models.AuthToken
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.models.BlogPost
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.persistance.BlogPostDao
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.respository.JobManager
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.respository.NetworkBoundResource
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.session.SessionManager
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.DataState
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.Response
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.ResponseType
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.ui.main.create_blog.state.CreateBlogViewState
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.util.AbsentLiveData
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.util.ApiSuccessResponse
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.util.DateUtils
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.util.GenericApiResponse
import com.mfcwl.library.powerfulandroidapps_removedaggerandroid.util.SuccessHandling.Companion.RESPONSE_MUST_BECOME_CODINGWITHMITCH_MEMBER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@MainScope
class CreateBlogRepository
@Inject
constructor(
    val openApiMainService: OpenApiMainService,
    val blogPostDao: BlogPostDao,
    val sessionManager: SessionManager
) : JobManager("CreateBlogRepository") {

    private val TAG: String = "AppDebug"

    fun createNewBlogPost(
        authToken: AuthToken,
        title: RequestBody,
        body: RequestBody,
        image: MultipartBody.Part?
    ): LiveData<DataState<CreateBlogViewState>> {
        return object :
            NetworkBoundResource<BlogCreateUpdateResponse, BlogPost, CreateBlogViewState>(
                sessionManager.isConnectedToTheInternet(),
                true,
                true,
                false
            ) {

            // not applicable
            override suspend fun createCacheRequestAndReturn() {

            }

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<BlogCreateUpdateResponse>) {

                // If they don't have a paid membership account it will still return a 200
                // Need to account for that
                if (!response.body.response.equals(RESPONSE_MUST_BECOME_CODINGWITHMITCH_MEMBER)) {
                    val updatedBlogPost = BlogPost(
                        response.body.pk,
                        response.body.title,
                        response.body.slug,
                        response.body.body,
                        response.body.image,
                        DateUtils.convertServerStringDateToLong(response.body.date_updated),
                        response.body.username
                    )
                    updateLocalDb(updatedBlogPost)
                }

                withContext(Dispatchers.Main) {
                    // finish with success response
                    onCompleteJob(
                        DataState.data(
                            null,
                            Response(response.body.response, ResponseType.Dialog())
                        )
                    )
                }
            }

            override fun createCall(): LiveData<GenericApiResponse<BlogCreateUpdateResponse>> {
                return openApiMainService.createBlog(
                    "Token ${authToken.token!!}",
                    title,
                    body,
                    image
                )
            }

            // not applicable
            override fun loadFromCache(): LiveData<CreateBlogViewState> {
                return AbsentLiveData.create()
            }

            override suspend fun updateLocalDb(cacheObject: BlogPost?) {
                cacheObject?.let {
                    blogPostDao.insert(it)
                }
            }

            override fun setJob(job: Job) {
                addJob("createNewBlogPost", job)
            }

        }.asLiveData()
    }

}