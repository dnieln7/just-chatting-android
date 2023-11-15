package xyz.dnieln7.domain.validation

enum class PasswordValidationError {
    EMPTY,
    TOO_SHORT,
}

const val MINIMUM_PASSWORD_LENGTH = 8
