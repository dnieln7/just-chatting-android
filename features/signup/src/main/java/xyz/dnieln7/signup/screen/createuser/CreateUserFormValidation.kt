package xyz.dnieln7.signup.screen.createuser

import xyz.dnieln7.domain.validation.ValidationStatus

data class CreateUserFormValidation(
    val emailValidation: ValidationStatus.Email = ValidationStatus.Email.Invalid.EMPTY,
    val usernameValidation: ValidationStatus.Text = ValidationStatus.Text.Invalid.EMPTY,
) {

    fun isValid(): Boolean {
        return emailValidation == ValidationStatus.Email.Valid && usernameValidation == ValidationStatus.Text.Valid
    }
}
