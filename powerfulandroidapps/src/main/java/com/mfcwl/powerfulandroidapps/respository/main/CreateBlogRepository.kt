package com.mfcwl.powerfulandroidapps.respository.main

import androidx.lifecycle.LiveData
import com.mfcwl.powerfulandroidapps.api.main.OpenApiMainService
import com.mfcwl.powerfulandroidapps.api.main.responses.BlogCreateUpdateResponse
import com.mfcwl.powerfulandroidapps.models.AuthToken
import com.mfcwl.powerfulandroidapps.models.BlogPost
import com.mfcwl.powerfulandroidapps.persistance.BlogPostDao
import com.mfcwl.powerfulandroidapps.respository.JobManager
import com.mfcwl.powerfulandroidapps.respository.NetworkBoundResource
import com.mfcwl.powerfulandroidapps.session.SessionManager
import com.mfcwl.powerfulandroidapps.ui.DataState
import com.mfcwl.powerfulandroidapps.ui.Response
import com.mfcwl.powerfulandroidapps.ui.ResponseType
import com.mfcwl.powerfulandroidapps.ui.main.create_blog.state.CreateBlogViewState
import com.mfcwl.powerfulandroidapps.util.AbsentLiveData
import com.mfcwl.powerfulandroidapps.util.ApiSuccessResponse
import com.mfcwl.powerfulandroidapps.util.DateUtils
import com.mfcwl.powerfulandroidapps.util.GenericApiResponse
import com.mfcwl.powerfulandroidapps.util.SuccessHandling.Companion.RESPONSE_MUST_BECOME_CODINGWITHMITCH_MEMBER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

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