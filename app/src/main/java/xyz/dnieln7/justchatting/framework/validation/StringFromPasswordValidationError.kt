package xyz.dnieln7.justchatting.framework.validation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.domain.validation.PasswordsValidationError

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
