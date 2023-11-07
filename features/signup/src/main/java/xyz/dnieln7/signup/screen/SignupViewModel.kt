package xyz.dnieln7.signup.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.usecase.GetEmailAvailabilityUseCase
import xyz.dnieln7.domain.usecase.SignupUseCase
import xyz.dnieln7.domain.usecase.ValidateEmailUseCase
import xyz.dnieln7.domain.usecase.ValidatePasswordsUseCase
import xyz.dnieln7.domain.usecase.ValidateSimpleTextUseCase
import xyz.dnieln7.signup.screen.createpassword.CreatePasswordState
import xyz.dnieln7.signup.screen.createuser.CreateUserState
import xyz.dnieln7.signup.screen.register.RegisterState
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordsUseCase: ValidatePasswordsUseCase,
    private val validateSimpleTextUseCase: ValidateSimpleTextUseCase,
    private val getEmailAvailabilityUseCase: GetEmailAvailabilityUseCase,
    private val signupUseCase: SignupUseCase,
) : ViewModel() {
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
            _createUserState.emit(CreateUserState.Loading)

            val emailError = validateEmailUseCase(email).swap().getOrNull()
            val usernameError = validateSimpleTextUseCase(username).swap().getOrNull()

            if (emailError == null && usernameError == null) {
                getEmailAvailabilityUseCase(email).fold(
                    {
                        _createUserState.emit(CreateUserState.Error(error = it))
                    },
                    {
                        this@SignupViewModel.email = email
                        this@SignupViewModel.username = username

                        _createUserState.emit(CreateUserState.Success)
                    }
                )
            } else {
                _createUserState.emit(
                    CreateUserState.Error(emailError = emailError, usernameError = usernameError)
                )
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
            val passwordsError = validatePasswordsUseCase(password, password2).swap().getOrNull()

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

            signupUseCase(email, password, username).fold(
                {
                    _registerState.emit(RegisterState.Error(it))
                },
                {
                    _registerState.emit(RegisterState.Success)
                }
            )
        }
    }
}