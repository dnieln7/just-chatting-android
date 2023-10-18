package xyz.dnieln7.composable.string

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import xyz.dnieln7.composable.R
import xyz.dnieln7.justchatting.domain.validation.PasswordsValidationError

// TODO: 17/10/23 Should be part of textfields behaviour?
@Composable
@ReadOnlyComposable
fun stringFromPasswordsValidationError(passwordsValidationError: PasswordsValidationError?): String? {
    return when (passwordsValidationError) {
        PasswordsValidationError.EMPTY -> stringResource(R.string.empty_text_error)
        PasswordsValidationError.LENGTH_LESS_THAN_12 -> stringResource(R.string.password_length_error)
        PasswordsValidationError.NOT_EQUAL -> stringResource(R.string.passwords_not_equal_error)
        null -> null
    }
}
