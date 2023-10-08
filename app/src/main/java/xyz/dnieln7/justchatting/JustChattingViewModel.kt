package xyz.dnieln7.justchatting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.justchatting.di.common.IO
import xyz.dnieln7.justchatting.domain.usecase.GetUserUserCase
import javax.inject.Inject

@HiltViewModel
class JustChattingViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val getUserUserCase: GetUserUserCase,
) : ViewModel() {

    private val _state = MutableStateFlow<InitializedState>(InitializedState.Loading)
    val state get() = _state.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            getUserUserCase().fold(
                {
                    _state.emit(InitializedState.NotAuthenticated)
                },
                {
                    _state.emit(InitializedState.Authenticated)
                }
            )
        }
    }

    sealed class InitializedState {
        object Loading : InitializedState()
        object NotAuthenticated : InitializedState()
        object Authenticated : InitializedState()
    }
}
