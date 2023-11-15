package xyz.dnieln7.composable.string

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import xyz.dnieln7.composable.R
import xyz.dnieln7.domain.validation.PasswordValidationError

@Composable
@ReadOnlyComposable
fun stringFromPasswordValidationError(passwordValidationError: PasswordValidationError?): String? {
    return when (passwordValidationError) {
        PasswordValidationError.EMPTY -> stringResource(R.string.empty_text_error)
        PasswordValidationError.TOO_SHORT -> stringResource(R.string.password_length_error)
        null -> null
    }
}
