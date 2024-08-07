package xyz.dnieln7.login.screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xyz.dnieln7.domain.usecase.ValidateEmailUseCase
import javax.inject.Inject

@HiltViewModel
class LoginFormViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
) : ViewModel() {

    private val _form = MutableStateFlow(LoginForm())
    val form get() = _form.asStateFlow()

    private val _validation = MutableStateFlow(LoginFormValidation())
    val validation get() = _validation.asStateFlow()

    fun updateEmail(email: String) {
        _form.update { it.copy(email = email) }

        val validationStatus = validateEmailUseCase(email)

        _validation.update { it.copy(initialized = true, emailValidation = validationStatus) }
    }

    fun updatePassword(password: String) {
        _form.update { it.copy(password = password) }
    }
}