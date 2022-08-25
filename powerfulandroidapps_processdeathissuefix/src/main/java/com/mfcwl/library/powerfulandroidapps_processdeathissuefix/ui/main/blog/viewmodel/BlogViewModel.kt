package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.bumptech.glide.RequestManager
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.persistance.BlogQueryUtils
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.respository.main.BlogRepository
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.session.SessionManager
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.BaseViewModel
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.DataState
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.Loading
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.state.BlogStateEvent
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.state.BlogViewState
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.util.AbsentLiveData
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.util.PreferenceKeys.Companion.BLOG_FILTER
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.util.PreferenceKeys.Companion.BLOG_ORDER
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class BlogViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val blogRepository: BlogRepository,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : BaseViewModel<BlogStateEvent, BlogViewState>() {

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

    override fun handleStateEvent(stateEvent: BlogStateEvent): LiveData<DataState<BlogViewState>> {
        when (stateEvent) {

            is BlogStateEvent.BlogSearchEvent -> {
                Log.d(TAG, "Blog Search Event...")
                clearLayoutManagerState()
                return sessionManager.cachedToken.value?.let { authToken ->
                    blogRepository.searchBlogPosts(
                        authToken = authToken,
                        query = getSearchQuery(),
                        filterAndOrder = getOrder() + getFilter(),
                        page = getPage()
                    )
                } ?: AbsentLiveData.create()
            }

            is BlogStateEvent.RestoreBlogListFromCache -> {
                return blogRepository.restoreBlogListFromCache(
                    query = getSearchQuery(),
                    filterAndOrder = getOrder() + getFilter(),
                    page = getPage()
                )
            }

            is BlogStateEvent.CheckAuthorOfBlogPost -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    blogRepository.isAuthorOfBlogPost(
                        authToken = authToken,
                        slug = getSlug()
                    )
                } ?: AbsentLiveData.create()
            }

            is BlogStateEvent.DeleteBlogPostEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    blogRepository.deleteBlogPost(
                        authToken = authToken,
                        blogPost = getBlogPost()
                    )
                } ?: AbsentLiveData.create()
            }

            is BlogStateEvent.UpdateBlogPostEvent -> {

                return sessionManager.cachedToken.value?.let { authToken ->

                    val title = RequestBody.create(
                        MediaType.parse("text/plain"),
                        stateEvent.title
                    )
                    val body = RequestBody.create(
                        MediaType.parse("text/plain"),
                        stateEvent.body
                    )

                    blogRepository.updateBlogPost(
                        authToken = authToken,
                        slug = getSlug(),
                        title = title,
                        body = body,
                        image = stateEvent.image
                    )
                } ?: AbsentLiveData.create()
            }

            is BlogStateEvent.None -> {
                return object : LiveData<DataState<BlogViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = DataState(null, Loading(false), null)
                    }
                }
            }
        }
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

    fun cancelActiveJobs() {
        blogRepository.cancelActiveJobs() // cancel active jobs
        handlePendingData() // hide progress bar
    }

    fun handlePendingData() {
        setStateEvent(BlogStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}