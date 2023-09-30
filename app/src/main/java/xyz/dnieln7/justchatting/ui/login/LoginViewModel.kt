package xyz.dnieln7.justchatting.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.justchatting.di.common.IO
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.None)
    val state get() = _state.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(dispatcher) {
            _state.emit(LoginState.Loading)
            delay(3000)
            _state.emit(LoginState.Success)
        }
    }

    fun onLoggedIn() {
        viewModelScope.launch(dispatcher) {
            _state.emit(LoginState.None)
        }
    }
}