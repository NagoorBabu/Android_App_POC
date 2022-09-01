package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.main

import android.util.Log
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.api.GenericResponse
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.api.main.OpenApiMainService
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.api.main.responses.BlogCreateUpdateResponse
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.api.main.responses.BlogListSearchResponse
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main.MainScope
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.models.AuthToken
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.models.BlogPost
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.persistance.BlogPostDao
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.persistance.returnOrderedBlogQuery
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.NetworkBoundResource
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.buildError
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.safeApiCall
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.safeCacheCall
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.session.SessionManager
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.blog.state.BlogViewState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.blog.state.BlogViewState.*
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.*
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.SuccessHandling.Companion.RESPONSE_HAS_PERMISSION_TO_EDIT
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.SuccessHandling.Companion.RESPONSE_NO_PERMISSION_TO_EDIT
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.SuccessHandling.Companion.SUCCESS_BLOG_DELETED
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@FlowPreview
@MainScope
class BlogRepositoryImpl
@Inject
constructor(
    val openApiMainService: OpenApiMainService,
    val blogPostDao: BlogPostDao,
    val sessionManager: SessionManager
) : BlogRepository {

    private val TAG: String = "AppDebug"
    override fun searchBlogPosts(
        authToken: AuthToken,
        query: String,
        filterAndOrder: String,
        page: Int,
        stateEvent: StateEvent
    ): Flow<DataState<BlogViewState>> {
        return object : NetworkBoundResource<BlogListSearchResponse, List<BlogPost>, BlogViewState>(
            dispatcher = IO,
            stateEvent = stateEvent,
            apiCall = {
                openApiMainService.searchListBlogPosts(
                    "Token ${authToken.token!!}",
                    query = query,
                    ordering = filterAndOrder,
                    page = page
                )
            },
            cacheCall = {
                blogPostDao.returnOrderedBlogQuery(
                    query = query,
                    filterAndOrder = filterAndOrder,
                    page = page
                )
            }
        ) {
            override suspend fun updateCache(networkObject: BlogListSearchResponse) {
                val blogPostList = networkObject.toList()
                withContext(IO) {
                    for (blogPost in blogPostList) {
                        try {
                            // Launch each insert as a separate job to be executed in parallel
                            launch {
                                Log.d(TAG, "updateLocalDb: inserting blog: ${blogPost}")
                                blogPostDao.insert(blogPost)
                            }
                        } catch (e: Exception) {
                            Log.e(
                                TAG,
                                "updateLocalDb: error updating cache data on blog post with slug: ${blogPost.slug}. " +
                                        "${e.message}"
                            )
                            // Could send an error report here or something but I don't think you should throw an error to the UI
                            // Since there could be many blog posts being inserted/updated.
                        }
                    }
                }
            }

            override fun handleCacheSuccess(resultObj: List<BlogPost>): DataState<BlogViewState> {
                val viewState = BlogViewState(
                    blogFields = BlogFields(
                        blogList = resultObj
                    )
                )
                return DataState.data(
                    response = null,
                    data = viewState,
                    stateEvent = stateEvent
                )
            }

        }.result
    }

    override fun restoreBlogListFromCache(
        query: String,
        filterAndOrder: String,
        page: Int,
        stateEvent: StateEvent
    ) = flow {

        val cacheResult = safeCacheCall(IO) {
            blogPostDao.returnOrderedBlogQuery(
                query = query,
                filterAndOrder = filterAndOrder,
                page = page
            )
        }
        emit(
            object : CacheResponseHandler<BlogViewState, List<BlogPost>>(
                response = cacheResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(
                    resultObj: List<BlogPost>
                ): DataState<BlogViewState> {
                    val viewState = BlogViewState(
                        blogFields = BlogFields(
                            blogList = resultObj
                        )
                    )
                    return DataState.data(
                        response = null,
                        data = viewState,
                        stateEvent = stateEvent
                    )
                }

            }.getResult()
        )
    }

    override fun isAuthorOfBlogPost(
        authToken: AuthToken,
        slug: String,
        stateEvent: StateEvent
    ) = flow {
        val apiResult = safeApiCall(IO) {
            openApiMainService.isAuthorOfBlogPost(
                "Token ${authToken.token!!}",
                slug
            )
        }
        emit(
            object : ApiResponseHandler<BlogViewState, GenericResponse>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: GenericResponse): DataState<BlogViewState> {
                    val viewState = BlogViewState(
                        viewBlogFields = ViewBlogFields(
                            isAuthorOfBlogPost = false
                        )
                    )
                    return when {

                        resultObj.response.equals(RESPONSE_NO_PERMISSION_TO_EDIT) -> {
                            DataState.data(
                                response = null,
                                data = viewState,
                                stateEvent = stateEvent
                            )
                        }

                        resultObj.response.equals(RESPONSE_HAS_PERMISSION_TO_EDIT) -> {
                            viewState.viewBlogFields.isAuthorOfBlogPost = true
                            DataState.data(
                                response = null,
                                data = viewState,
                                stateEvent = stateEvent
                            )
                        }

                        else -> {
                            buildError(
                                ERROR_UNKNOWN,
                                UIComponentType.None(),
                                stateEvent
                            )
                        }
                    }
                }
            }.getResult()
        )
    }

    override fun deleteBlogPost(
        authToken: AuthToken,
        blogPost: BlogPost,
        stateEvent: StateEvent
    ) = flow {
        val apiResult = safeApiCall(IO) {
            openApiMainService.deleteBlogPost(
                "Token ${authToken.token!!}",
                blogPost.slug
            )
        }
        emit(
            object : ApiResponseHandler<BlogViewState, GenericResponse>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: GenericResponse): DataState<BlogViewState> {

                    if (resultObj.response == SUCCESS_BLOG_DELETED) {
                        blogPostDao.deleteBlogPost(blogPost)
                        return DataState.data(
                            response = Response(
                                message = SUCCESS_BLOG_DELETED,
                                uiComponentType = UIComponentType.Toast(),
                                messageType = MessageType.Success()
                            ),
                            stateEvent = stateEvent
                        )
                    } else {
                        return buildError(
                            ERROR_UNKNOWN,
                            UIComponentType.Dialog(),
                            stateEvent
                        )
                    }
                }
            }.getResult()
        )
    }

    override fun updateBlogPost(
        authToken: AuthToken,
        slug: String,
        title: RequestBody,
        body: RequestBody,
        image: MultipartBody.Part?,
        stateEvent: StateEvent
    ) = flow {

        val apiResult = safeApiCall(IO) {
            openApiMainService.updateBlog(
                "Token ${authToken.token!!}",
                slug,
                title,
                body,
                image
            )
        }
        emit(
            object : ApiResponseHandler<BlogViewState, BlogCreateUpdateResponse>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: BlogCreateUpdateResponse): DataState<BlogViewState> {

                    val updatedBlogPost = resultObj.toBlogPost()

                    blogPostDao.updateBlogPost(
                        updatedBlogPost.pk,
                        updatedBlogPost.title,
                        updatedBlogPost.body,
                        updatedBlogPost.image
                    )

                    return DataState.data(
                        response = Response(
                            message = resultObj.response,
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Success()
                        ),
                        data = BlogViewState(
                            viewBlogFields = ViewBlogFields(
                                blogPost = updatedBlogPost
                            )
                        ),
                        stateEvent = stateEvent
                    )

                }

            }.getResult()
        )
    }


}




