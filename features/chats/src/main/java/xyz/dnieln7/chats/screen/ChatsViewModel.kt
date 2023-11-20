package xyz.dnieln7.chats.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.usecase.GetChatsUseCase
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val getChatsUseCase: GetChatsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ChatsState>(ChatsState.Loading)
    val state get() = _state.asStateFlow()

    init {
        getChats()
    }

    fun getChats() {
        viewModelScope.launch(dispatcher) {
            _state.emit(ChatsState.Loading)

            getChatsUseCase().fold(
                {
                    _state.emit(ChatsState.Error(it))
                },
                {
                    _state.emit(ChatsState.Success(it))
                }
            )
        }
    }
}