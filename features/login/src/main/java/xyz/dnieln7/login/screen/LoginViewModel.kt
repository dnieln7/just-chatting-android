package xyz.dnieln7.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.None)
    val state get() = _state.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(dispatcher) {
            _state.emit(LoginState.Loading)

            loginUseCase(email, password).fold(
                {
                    _state.emit(LoginState.Error(it))
                },
                {
                    _state.emit(LoginState.Success)
                }
            )
        }
    }

    fun onLoggedIn() {
        viewModelScope.launch(dispatcher) {
            _state.emit(LoginState.None)
        }
    }
}