package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.blog.viewmodel

import android.content.SharedPreferences
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.di.main.MainScope
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.persistance.BlogQueryUtils
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.respository.main.BlogRepositoryImpl
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.session.SessionManager
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.BaseViewModel
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.blog.state.BlogStateEvent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.blog.state.BlogViewState
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.*
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.ErrorHandling.Companion.INVALID_STATE_EVENT
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.PreferenceKeys.Companion.BLOG_FILTER
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.util.PreferenceKeys.Companion.BLOG_ORDER
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@MainScope
class BlogViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val blogRepository: BlogRepositoryImpl,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : BaseViewModel<BlogViewState>() {

    init {
        setBlogFilter(
            sharedPreferences.getString(
                BLOG_FILTER,
                BlogQueryUtils.BLOG_FILTER_DATE_UPDATED
            )
        )
        sharedPreferences.getString(
            BLOG_ORDER,
            BlogQueryUtils.BLOG_ORDER_ASC
        )?.let {
            setBlogOrder(
                it
            )
        }
    }

    override fun handleNewData(stateEvent: StateEvent?, data: BlogViewState) {

        data.blogFields.let { blogFields ->

            blogFields.blogList?.let { blogList ->
                handleIncomingBlogListData(data)
            }

            blogFields.isQueryExhausted?.let { isQueryExhausted ->
                setQueryExhausted(isQueryExhausted)
            }
        }

        data.viewBlogFields.let { viewBlogFields ->

            viewBlogFields.blogPost?.let { blogPost ->
                setBlogPost(blogPost)
            }

            viewBlogFields.isAuthorOfBlogPost?.let { isAuthor ->
                setIsAuthorOfBlogPost(isAuthor)
            }
        }

        data.updatedBlogFields.let { updatedBlogFields ->

            updatedBlogFields.updatedImageUri?.let { uri ->
                setUpdatedUri(uri)
            }

            updatedBlogFields.updatedBlogTitle?.let { title ->
                setUpdatedTitle(title)
            }

            updatedBlogFields.updatedBlogBody?.let { body ->
                setUpdatedBody(body)
            }
        }

        _activeStateEventTracker.removeStateEvent(stateEvent)
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        sessionManager.cachedToken.value?.let { authToken ->
            val job: Flow<DataState<BlogViewState>> = when (stateEvent) {

                is BlogStateEvent.BlogSearchEvent -> {
                    clearLayoutManagerState()
                    blogRepository.searchBlogPosts(
                        stateEvent = stateEvent,
                        authToken = authToken,
                        query = getSearchQuery(),
                        filterAndOrder = getOrder() + getFilter(),
                        page = getPage()
                    )
                }

                is BlogStateEvent.RestoreBlogListFromCache -> {
                    blogRepository.restoreBlogListFromCache(
                        stateEvent = stateEvent,
                        query = getSearchQuery(),
                        filterAndOrder = getOrder() + getFilter(),
                        page = getPage()
                    )
                }

                is BlogStateEvent.CheckAuthorOfBlogPost -> {
                    blogRepository.isAuthorOfBlogPost(
                        stateEvent = stateEvent,
                        authToken = authToken,
                        slug = getSlug()
                    )
                }

                is BlogStateEvent.DeleteBlogPostEvent -> {
                    blogRepository.deleteBlogPost(
                        stateEvent = stateEvent,
                        authToken = authToken,
                        blogPost = getBlogPost()
                    )
                }

                is BlogStateEvent.UpdateBlogPostEvent -> {
                    val title = RequestBody.create(
                        MediaType.parse("text/plain"),
                        stateEvent.title
                    )
                    val body = RequestBody.create(
                        MediaType.parse("text/plain"),
                        stateEvent.body
                    )

                    blogRepository.updateBlogPost(
                        stateEvent = stateEvent,
                        authToken = authToken,
                        slug = getSlug(),
                        title = title,
                        body = body,
                        image = stateEvent.image
                    )
                }

                else -> {
                    flow {
                        emit(
                            DataState.error(
                                response = Response(
                                    message = INVALID_STATE_EVENT,
                                    uiComponentType = UIComponentType.None(),
                                    messageType = MessageType.Error()
                                ),
                                stateEvent = stateEvent
                            )
                        )
                    }
                }
            }
            launchJob(stateEvent, job)
        } ?: sessionManager.logout()

    }

    override fun initNewViewState(): BlogViewState {
        return BlogViewState()
    }

    fun saveFilterOptions(filter: String, order: String) {
        editor.putString(BLOG_FILTER, filter)
        editor.apply()

        editor.putString(BLOG_ORDER, order)
        editor.apply()
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}



