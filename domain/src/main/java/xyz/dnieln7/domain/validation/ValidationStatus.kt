package xyz.dnieln7.domain.validation

sealed interface ValidationStatus {
    sealed interface Email : ValidationStatus {
        object Valid : Email

        enum class Invalid : Email {
            EMPTY,
            MALFORMED,
        }
    }

    sealed interface Password : ValidationStatus {
        object Valid : Password

        enum class Invalid : Password {
            EMPTY,
            TOO_SHORT,
        }
    }

    sealed interface Text : ValidationStatus {
        object Valid : Text

        enum class Invalid : Text {
            EMPTY,
        }
    }
}

const val MINIMUM_PASSWORD_LENGTH = 8
