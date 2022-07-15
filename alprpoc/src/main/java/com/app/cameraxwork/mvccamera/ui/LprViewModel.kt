package com.app.cameraxwork.mvccamera.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.cameraxwork.mvccamera.model.Lpr
import com.app.cameraxwork.mvccamera.repository.LprRepository
import com.app.cameraxwork.mvccamera.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File

class LprViewModel
@ViewModelInject
constructor(
    private val lprRepository: LprRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Lpr>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Lpr>>>
        get() = _dataState

    @ExperimentalCoroutinesApi
    fun setStateEvent(lprStateEvent: LprStateEvent) {
        viewModelScope.launch {
            when (lprStateEvent) {

                is LprStateEvent.GetOcrValueEvent -> {
                    lprRepository.getOcrValue(lprStateEvent.file)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)

                }

                is LprStateEvent.None -> {
//                    No Action
                }
            }

        }
    }

}

sealed class LprStateEvent {

    class GetOcrValueEvent(val file: File) : LprStateEvent()

    class None : LprStateEvent()
}