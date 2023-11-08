package xyz.dnieln7.domain.validation

enum class PasswordsValidationError {
    EMPTY,
    LENGTH_LESS_THAN_12,
    NOT_EQUAL,
}
