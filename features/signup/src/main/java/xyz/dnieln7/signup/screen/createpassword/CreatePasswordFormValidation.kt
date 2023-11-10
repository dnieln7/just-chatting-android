package xyz.dnieln7.signup.screen.createpassword

import xyz.dnieln7.domain.validation.PasswordValidationError

data class CreatePasswordFormValidation(
    val passwordValidationError: PasswordValidationError? = PasswordValidationError.EMPTY,
    val passwordsAreEqual: Boolean = false,
)
