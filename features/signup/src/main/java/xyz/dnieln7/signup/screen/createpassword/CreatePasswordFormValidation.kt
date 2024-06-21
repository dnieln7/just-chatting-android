package xyz.dnieln7.signup.screen.createpassword

import xyz.dnieln7.domain.validation.ValidationStatus

data class CreatePasswordFormValidation(
    val passwordsMatch: Boolean? = null,
    val passwordValidation: ValidationStatus.Password = ValidationStatus.Password.Invalid.EMPTY,
) {

    fun isValid(): Boolean {
        return passwordsMatch == true && passwordValidation == ValidationStatus.Password.Valid
    }
}
