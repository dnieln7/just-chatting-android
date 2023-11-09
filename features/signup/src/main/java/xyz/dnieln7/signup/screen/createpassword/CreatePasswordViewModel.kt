package xyz.dnieln7.signup.screen.createpassword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.usecase.ValidatePasswordsUseCase
import xyz.dnieln7.signup.navigation.CREATE_PASSWORD_EMAIL
import xyz.dnieln7.signup.navigation.CREATE_PASSWORD_USERNAME
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val validatePasswordsUseCase: ValidatePasswordsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<CreatePasswordState>(CreatePasswordState.None)
    val state get() = _state.asStateFlow()

    private val email: String = savedStateHandle[CREATE_PASSWORD_EMAIL]!!
    private val username: String = savedStateHandle[CREATE_PASSWORD_USERNAME]!!

    fun createPassword(password: String, password2: String) {
        viewModelScope.launch(dispatcher) {
            val passwordsError = validatePasswordsUseCase(password, password2).swap().getOrNull()

            if (passwordsError == null) {
                _state.emit(CreatePasswordState.Success(email, password, username))
            } else {
                _state.emit(CreatePasswordState.Error(passwordsError))
            }
        }
    }

    fun resetState() {
        viewModelScope.launch(dispatcher) {
            _state.emit(CreatePasswordState.None)
        }
    }
}