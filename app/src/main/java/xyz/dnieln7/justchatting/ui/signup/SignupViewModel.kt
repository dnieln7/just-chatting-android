package xyz.dnieln7.justchatting.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.justchatting.domain.usecase.ValidateEmailUseCase
import xyz.dnieln7.justchatting.domain.usecase.ValidatePasswordsUseCase
import xyz.dnieln7.justchatting.domain.usecase.ValidateUsernameUseCase

class SignupViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    private val _state = MutableStateFlow<SignupState>(SignupState.CreateUser())
    val state get() = _state.asStateFlow()

    private lateinit var email: String
    private lateinit var username: String
    private lateinit var password: String

    fun createUser(email: String, username: String) {
        viewModelScope.launch(dispatcher) {
            delay(3000)

            val emailValidationError = ValidateEmailUseCase()(email).getOrNull()
            val usernameValidationError = ValidateUsernameUseCase()(username).getOrNull()

            if (emailValidationError == null && usernameValidationError == null) {
                this@SignupViewModel.email = email
                this@SignupViewModel.username = username

                _state.emit(SignupState.CreatePassword())
            } else {
                _state.emit(SignupState.CreateUser(emailValidationError, usernameValidationError))
            }
        }
    }

    fun createPassword(password: String, password2: String) {
        viewModelScope.launch(dispatcher) {
            delay(3000)

            val passwordsValidationError =
                ValidatePasswordsUseCase()(password, password2).getOrNull()

            if (passwordsValidationError == null) {
                this@SignupViewModel.password = password

                _state.emit(SignupState.Register(RegisterStatus.Registering))
                register()
            } else {
                _state.emit(SignupState.CreatePassword(passwordsValidationError))
            }
        }
    }

    fun register() {
        viewModelScope.launch(dispatcher) {
            delay(3000)
            _state.emit(SignupState.Register(RegisterStatus.Registered))
        }
    }
}