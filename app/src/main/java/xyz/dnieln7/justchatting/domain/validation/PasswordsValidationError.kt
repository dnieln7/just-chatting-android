package xyz.dnieln7.justchatting.domain.validation

enum class PasswordsValidationError {
    EMPTY,
    LENGTH_LESS_THAN_12,
    NOT_EQUAL,
}
