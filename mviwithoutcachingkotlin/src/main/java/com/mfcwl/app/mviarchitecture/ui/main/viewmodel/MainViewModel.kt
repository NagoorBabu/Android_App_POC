package com.mfcwl.app.mviarchitecture.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mfcwl.app.mviarchitecture.model.BlogPost
import com.mfcwl.app.mviarchitecture.model.User
import com.mfcwl.app.mviarchitecture.repository.main.MainRepository
import com.mfcwl.app.mviarchitecture.ui.main.state.MainStateEvent
import com.mfcwl.app.mviarchitecture.ui.main.state.MainViewState
import com.mfcwl.app.mviarchitecture.util.AbsentLiveData
import com.mfcwl.app.mviarchitecture.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }

        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        when (stateEvent) {

            is MainStateEvent.GetBlogPostsEvent -> {
                return MainRepository.getBlogPosts()
            }

            is MainStateEvent.GetUserEvent -> {
                return MainRepository.getUser(stateEvent.userId)
            }

            is MainStateEvent.None -> {
                return AbsentLiveData.create()
            }
        }

    }

    fun setBlogListData(blogPosts: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogposts = blogPosts
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let {
            it
        } ?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent) {
        val state: MainStateEvent = event
        _stateEvent.value = state

    }
}