package xyz.dnieln7.signup.screen.createuser

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xyz.dnieln7.domain.usecase.ValidateEmailUseCase
import xyz.dnieln7.domain.usecase.ValidateSimpleTextUseCase
import javax.inject.Inject

@HiltViewModel
class CreateUserFormViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateSimpleTextUseCase: ValidateSimpleTextUseCase,
) : ViewModel() {

    private val _form = MutableStateFlow(CreateUserForm())
    val form get() = _form.asStateFlow()

    private val _validation = MutableStateFlow(CreateUserFormValidation())
    val validation get() = _validation.asStateFlow()

    fun updateEmail(email: String) {
        _form.update { it.copy(email = email) }

        val validationStatus = validateEmailUseCase(email)

        _validation.update { it.copy(emailValidation = validationStatus) }
    }

    fun updateUsername(username: String) {
        _form.update { it.copy(username = username) }

        val validationStatus = validateSimpleTextUseCase(username)

        _validation.update { it.copy(usernameValidation = validationStatus) }
    }
}