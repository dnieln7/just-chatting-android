package xyz.dnieln7.composable.string

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import xyz.dnieln7.composable.R
import xyz.dnieln7.domain.validation.EmailValidationError

@Composable
@ReadOnlyComposable
fun stringFromEmailValidationError(emailValidationError: EmailValidationError?): String? {
    return when (emailValidationError) {
        EmailValidationError.EMPTY -> stringResource(R.string.empty_text_error)
        EmailValidationError.NOT_AN_EMAIL -> stringResource(R.string.email_not_valid_error)
        null -> null
    }
}
