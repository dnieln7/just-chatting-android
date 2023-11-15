package xyz.dnieln7.signup.screen.createpassword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xyz.dnieln7.domain.usecase.ValidatePasswordUseCase
import xyz.dnieln7.signup.navigation.CREATE_PASSWORD_EMAIL
import xyz.dnieln7.signup.navigation.CREATE_PASSWORD_USERNAME
import javax.inject.Inject

@HiltViewModel
class CreatePasswordFormViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : ViewModel() {

    private val email: String = savedStateHandle[CREATE_PASSWORD_EMAIL]!!
    private val username: String = savedStateHandle[CREATE_PASSWORD_USERNAME]!!

    private val _form = MutableStateFlow(CreatePasswordForm(email, username))
    val form get() = _form.asStateFlow()

    private val _validation = MutableStateFlow(CreatePasswordFormValidation())
    val validation get() = _validation.asStateFlow()

    fun updatePassword(password: String) {
        _form.update { it.copy(password = password) }

        validatePasswordUseCase(password).fold(
            { error ->
                _validation.update { it.copy(passwordValidationError = error) }
            },
            {
                _validation.update { it.copy(passwordValidationError = null) }
            },
        )

        _validation.update {
            it.copy(passwordsMatch = password == _form.value.passwordConfirm)
        }
    }

    fun updatePasswordConfirm(passwordConfirm: String) {
        _form.update { it.copy(passwordConfirm = passwordConfirm) }
        _validation.update {
            it.copy(passwordsMatch = _form.value.password == passwordConfirm)
        }
    }
}