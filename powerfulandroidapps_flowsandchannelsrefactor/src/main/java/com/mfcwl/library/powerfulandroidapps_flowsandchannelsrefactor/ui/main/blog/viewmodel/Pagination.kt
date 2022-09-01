package com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.blog.viewmodel

import android.util.Log
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.blog.state.BlogStateEvent
import com.mfcwl.library.powerfulandroidapps_flowsandchannelsrefactor.ui.main.blog.state.BlogViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
fun BlogViewModel.resetPage() {
    val update = getCurrentViewStateOrNew()
    update.blogFields.page = 1
    setViewState(update)
}

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
fun BlogViewModel.refreshFromCache() {
    setQueryExhausted(false)
    setStateEvent(BlogStateEvent.RestoreBlogListFromCache())
}

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
fun BlogViewModel.loadFirstPage() {
    setQueryExhausted(false)
    resetPage()
    setStateEvent(BlogStateEvent.BlogSearchEvent())
    Log.e(TAG, "BlogViewModel: loadFirstPage: ${viewState.value!!.blogFields.searchQuery}")
}

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
private fun BlogViewModel.incrementPageNumber() {
    val update = getCurrentViewStateOrNew()
    val page = update.copy().blogFields.page // get current page
    update.blogFields.page = page?.plus(1)
    setViewState(update)
}

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
fun BlogViewModel.nextPage() {
    if (!isJobAlreadyActive(BlogStateEvent.BlogSearchEvent())
        && !viewState.value!!.blogFields.isQueryExhausted!!
    ) {
        Log.d(TAG, "BlogViewModel: Attempting to load next page...")
        incrementPageNumber()
        setStateEvent(BlogStateEvent.BlogSearchEvent())
    }
}

@FlowPreview
@OptIn(ExperimentalCoroutinesApi::class)
fun BlogViewModel.handleIncomingBlogListData(viewState: BlogViewState) {
    viewState.blogFields.let { blogFields ->
        blogFields.blogList?.let { setBlogListData(it) }
        blogFields.isQueryExhausted?.let { setQueryExhausted(it) }
    }
}


