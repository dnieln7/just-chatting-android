package xyz.dnieln7.signup.screen.createuser

import xyz.dnieln7.domain.validation.EmailValidationError
import xyz.dnieln7.domain.validation.SimpleTextValidationError

data class CreateUserFormValidation(
    val emailValidationError: EmailValidationError? = EmailValidationError.EMPTY,
    val usernameValidationError: SimpleTextValidationError? = SimpleTextValidationError.EMPTY,
)
