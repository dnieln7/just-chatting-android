package xyz.dnieln7.justchatting.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {
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