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
import xyz.dnieln7.justchatting.domain.usecase.ValidateSimpleTextUseCase
import xyz.dnieln7.justchatting.ui.signup.createpassword.CreatePasswordState
import xyz.dnieln7.justchatting.ui.signup.createuser.CreateUserState
import xyz.dnieln7.justchatting.ui.signup.register.RegisterState

class SignupViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {
    private val _createUserState = MutableStateFlow<CreateUserState>(CreateUserState.None)
    val createUserState get() = _createUserState.asStateFlow()

    private val _createPasswordState = MutableStateFlow<CreatePasswordState>(
        CreatePasswordState.None
    )
    val createPasswordState get() = _createPasswordState.asStateFlow()

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Loading)
    val registerState get() = _registerState.asStateFlow()

    private lateinit var email: String
    private lateinit var username: String
    private lateinit var password: String

    fun createUser(email: String, username: String) {
        viewModelScope.launch(dispatcher) {
            val emailError = ValidateEmailUseCase()(email).swap().getOrNull()
            val usernameError = ValidateSimpleTextUseCase()(username).swap().getOrNull()

            if (emailError == null && usernameError == null) {
                this@SignupViewModel.email = email
                this@SignupViewModel.username = username

                _createUserState.emit(CreateUserState.Success)
            } else {
                _createUserState.emit(CreateUserState.Error(emailError, usernameError))
            }
        }
    }

    fun onUserCreated() {
        viewModelScope.launch(dispatcher) {
            _createUserState.emit(CreateUserState.None)
        }
    }

    fun createPassword(password: String, password2: String) {
        viewModelScope.launch(dispatcher) {
            val passwordsError = ValidatePasswordsUseCase()(password, password2).swap().getOrNull()

            if (passwordsError == null) {
                this@SignupViewModel.password = password

                _createPasswordState.emit(CreatePasswordState.Success)
            } else {
                _createPasswordState.emit(CreatePasswordState.Error(passwordsError))
            }
        }
    }

    fun onPasswordCreated() {
        viewModelScope.launch(dispatcher) {
            _createPasswordState.emit(CreatePasswordState.None)
        }
    }

    fun register() {
        viewModelScope.launch(dispatcher) {
            _registerState.emit(RegisterState.Loading)
            delay(3000)
            _registerState.emit(RegisterState.Success)
        }
    }
}