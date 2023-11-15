package xyz.dnieln7.signup.screen.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.usecase.SignupUseCase
import xyz.dnieln7.signup.navigation.REGISTER_EMAIL
import xyz.dnieln7.signup.navigation.REGISTER_PASSWORD
import xyz.dnieln7.signup.navigation.REGISTER_USERNAME
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val signupUseCase: SignupUseCase,
) : ViewModel() {

    private val email: String = savedStateHandle[REGISTER_EMAIL]!!
    private val password: String = savedStateHandle[REGISTER_PASSWORD]!!
    private val username: String = savedStateHandle[REGISTER_USERNAME]!!

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Loading)
    val state get() = _state.asStateFlow()

    init {
        register()
    }

    fun register() {
        viewModelScope.launch(dispatcher) {
            _state.emit(RegisterState.Loading)

            signupUseCase(email, password, username).fold(
                {
                    _state.emit(RegisterState.Error(it))
                },
                {
                    _state.emit(RegisterState.Success)
                }
            )
        }
    }

    fun resetState() {
        viewModelScope.launch(dispatcher) {
            _state.emit(RegisterState.Loading)
        }
    }
}