package xyz.dnieln7.login.screen

import xyz.dnieln7.domain.validation.EmailValidationError

data class LoginFormValidation(
    val emailValidationError: EmailValidationError? = EmailValidationError.EMPTY,
)
